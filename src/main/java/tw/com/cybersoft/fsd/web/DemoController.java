package tw.com.cybersoft.fsd.web;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.joda.time.Instant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.cybersoft.fsd.security.SecurityUtil;

@RestController
public class DemoController {
	// helper for 7
	private SecurityUtil securityUtil;
	// 7
	private ProcessRuntime processRuntime;
	private TaskRuntime taskRuntime;
	private TaskAdminRuntime taskAdminRuntime;
	// 6
	private RuntimeService runtimeService;
	private TaskService taskService;

	public DemoController(SecurityUtil securityUtil, ProcessRuntime processRuntime, TaskRuntime taskRuntime,
			TaskAdminRuntime taskAdminRuntime, RuntimeService runtimeService, TaskService taskService) {
		this.securityUtil = securityUtil;
		this.processRuntime = processRuntime;
		this.taskRuntime = taskRuntime;
		this.taskAdminRuntime = taskAdminRuntime;
		this.runtimeService = runtimeService;
		this.taskService = taskService;
	}

	@GetMapping("/startLegacy")
	public String startLegacy(@RequestParam(defaultValue = "tommy") String user) {
		// 有放這行才吃得到process initiator = user, 但與spring security/userDetails無關
		Authentication.setAuthenticatedUserId(user);
		// 什麼都不擋
		org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("groupTest", Collections.singletonMap("startingVariable", "start!"));
		return processInstance.getId();
	}

	@GetMapping("/start")
	public String start(@RequestParam(defaultValue = "tommy") String user) {
		securityUtil.logInAs(user);
		// user必須要有ROLE_ACTIVITI_USER身分
		ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start()
				.withProcessDefinitionKey("groupTest").withVariable("startingVariable", "start!").build());
		return processInstance.getId();
	}

	@GetMapping("/findLegacy")
	public List<Object> findLegacy(@RequestParam(defaultValue = "tommy") String user) {
		// 什麼都不擋
		// 是透過taskCandidateOrAssigned(user)達到'收件夾'效果
		return taskService.createTaskQuery().taskCandidateOrAssigned(user).list().stream()
				.map(t -> ((TaskEntityImpl) t).getPersistentState()).collect(Collectors.toList());
	}

	@GetMapping("/find")
	public List<org.activiti.api.task.model.Task> find(@RequestParam(defaultValue = "tommy") String user) {
		securityUtil.logInAs(user);
		// user必須要有ROLE_ACTIVITI_USER
		// 只查得到assignee是user或candidateGroup對應到user擁有的GROUP_[XXX]身分
		return taskRuntime.tasks(Pageable.of(0, 10), TaskPayloadBuilder.tasks().build()).getContent();
	}

	@GetMapping("/findAdmin")
	public List<org.activiti.api.task.model.Task> findAdmin(@RequestParam(defaultValue = "gina") String user) {
		securityUtil.logInAs(user);
		// user必須要有ROLE_ACTIVITI_ADMIN
		// 類似legacy, 可查到其他人的案件
		return taskAdminRuntime.tasks(Pageable.of(0, 10), TaskPayloadBuilder.tasks().build()).getContent();
	}

	@GetMapping("/completeLegacy")
	public void completeLegacy(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		// 什麼都不擋
		taskService.complete(taskId, Collections.singletonMap("completeLegacy", Instant.now().toString()));
	}

	@GetMapping("/complete")
	public Task complete(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		securityUtil.logInAs(user);
		// user必須要有ROLE_ACTIVITI_USER
		// 只能complete自己是assignee的案件 (即使是群組件也必須先claim才能complete)
		return taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskId)
				.withVariable("complete", Instant.now().toString()).build());
	}

	@GetMapping("/completeAdmin")
	public Task completeAdmin(@RequestParam(defaultValue = "gina") String user, @RequestParam String taskId) {
		securityUtil.logInAs(user);
		// user必須要有ROLE_ACTIVITI_ADMIN
		// 類似legacy, 可完成其他人的案件
		return taskAdminRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskId)
				.withVariable("completeAdmin", Instant.now().toString()).build());
	}

	@GetMapping("/claimLegacy")
	public void claimLegacy(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		// 只能claim沒有assignee的案件
		taskService.claim(taskId, user);
	}

	@GetMapping("/claim")
	public Task claim(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		securityUtil.logInAs(user);
		// user必須要有ROLE_ACTIVITI_USER
		// candidateGroup對應到user擁有的GROUP_[XXX]身分且尚未被claim, 才能claim
		return taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskId).withAssignee(user).build());
	}

	@GetMapping("/claimAdmin")
	public Task claimAdmin(@RequestParam(defaultValue = "gina") String user, @RequestParam String taskId) {
		securityUtil.logInAs(user);
		// user必須要有ROLE_ACTIVITI_ADMIN
		// 類似legacy, 可claim非自己群組的案件
		return taskAdminRuntime.claim(TaskPayloadBuilder.claim().withTaskId(taskId).withAssignee(user).build());
	}

}

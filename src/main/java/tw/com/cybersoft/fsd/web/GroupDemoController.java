package tw.com.cybersoft.fsd.web;

import static tw.com.cybersoft.fsd.logic.Utils.nowStr;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskAdminRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tw.com.cybersoft.fsd.security.SecurityUtil;

@RestController
@RequestMapping("/group")
public class GroupDemoController {
	// helper for 7
	private SecurityUtil securityUtil;
	// 7
	private ProcessRuntime processRuntime;
	private TaskRuntime taskRuntime;
	private TaskAdminRuntime taskAdminRuntime;
	// 6
	private RuntimeService runtimeService;
	private TaskService taskService;

	private ObjectMapper mapper;

	public GroupDemoController(SecurityUtil securityUtil, ProcessRuntime processRuntime, TaskRuntime taskRuntime,
			TaskAdminRuntime taskAdminRuntime, RuntimeService runtimeService, TaskService taskService,
			ObjectMapper mapper) {
		this.securityUtil = securityUtil;
		this.processRuntime = processRuntime;
		this.taskRuntime = taskRuntime;
		this.taskAdminRuntime = taskAdminRuntime;
		this.runtimeService = runtimeService;
		this.taskService = taskService;
		this.mapper = mapper;
	}

	@GetMapping("/startLegacy")
	public String startLegacy(@RequestParam(defaultValue = "tommy") String user) {
		// ????????????????????????process initiator = user, ??????spring security/userDetails??????
		Authentication.setAuthenticatedUserId(user);
		// ???????????????
		org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("groupTest", Collections.singletonMap(nowStr(), "startLegacy"));
		return processInstance.getId();
	}

	@GetMapping("/start")
	public String start(@RequestParam(defaultValue = "tommy") String user) {
		securityUtil.logInAs(user);
		// user????????????ROLE_ACTIVITI_USER??????
		org.activiti.api.process.model.ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
				.start().withProcessDefinitionKey("groupTest").withVariable(nowStr(), "start").build());
		return processInstance.getId();
	}

	@GetMapping("/findLegacy")
	public List<Object> findLegacy(@RequestParam(defaultValue = "tommy") String user) {
		// ???????????????
		// ?????????taskCandidateOrAssigned(user)??????'?????????'??????
		return taskService.createTaskQuery().taskCandidateOrAssigned(user).includeProcessVariables().list().stream()
				.map(this::toMap).collect(Collectors.toList());
	}

	private Map<String, Object> toMap(org.activiti.engine.task.Task task) {
		TaskEntityImpl t = (TaskEntityImpl) task;
		Map<String, Object> map = (Map<String, Object>) t.getPersistentState();
		map.put("processVariables", t.getProcessVariables());
		map.put("id", task.getId());
		return map;
	}

	@GetMapping("/find")
	public List<Map<String, Object>> find(@RequestParam(defaultValue = "tommy") String user) {
		securityUtil.logInAs(user);
		// user????????????ROLE_ACTIVITI_USER
		// ????????????assignee???user???candidateGroup?????????user?????????GROUP_[XXX]??????
		return taskRuntime.tasks(Pageable.of(0, 10), TaskPayloadBuilder.tasks().build()).getContent().stream()
				.map(this::toMap).collect(Collectors.toList());
	}

	private Map<String, Object> toMap(org.activiti.api.task.model.Task task) {
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
		};
		Map<String, Object> map = mapper.convertValue(task, typeReference);
		// n+1 query problem
		List<VariableInstance> variables = taskRuntime
				.variables(TaskPayloadBuilder.variables().withTaskId(task.getId()).build());
		map.put("variables", variables);
		map.put("id", task.getId());
		return map;
	}

	@GetMapping("/findAdmin")
	public List<Map<String, Object>> findAdmin(@RequestParam(defaultValue = "gina") String user) {
		securityUtil.logInAs(user);
		// user????????????ROLE_ACTIVITI_ADMIN
		// ??????legacy, ???????????????????????????
		return taskAdminRuntime.tasks(Pageable.of(0, 10), TaskPayloadBuilder.tasks().build()).getContent().stream()
				.map(this::adminToMap).collect(Collectors.toList());
	}

	private Map<String, Object> adminToMap(org.activiti.api.task.model.Task task) {
		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
		};
		Map<String, Object> map = mapper.convertValue(task, typeReference);
		// n+1 query problem
		List<VariableInstance> variables = taskAdminRuntime
				.variables(TaskPayloadBuilder.variables().withTaskId(task.getId()).build());
		map.put("variables", variables);
		map.put("id", task.getId());
		return map;
	}

	@GetMapping("/completeLegacy")
	public void completeLegacy(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		// ???????????????
		// method???????????????
		taskService.complete(taskId, Collections.singletonMap(nowStr(), "completeLegacy"));
	}

	@GetMapping("/complete")
	public Map<String, Object> complete(@RequestParam(defaultValue = "tommy") String user,
			@RequestParam String taskId) {
		securityUtil.logInAs(user);
		// user????????????ROLE_ACTIVITI_USER
		// ??????complete?????????assignee????????? (??????????????????????????????claim??????complete)
		org.activiti.api.task.model.Task completed = taskRuntime
				.complete(TaskPayloadBuilder.complete().withTaskId(taskId).withVariable(nowStr(), "complete").build());
		return this.toMap(completed);
	}

	@GetMapping("/completeAdmin")
	public Map<String, Object> completeAdmin(@RequestParam(defaultValue = "gina") String user,
			@RequestParam String taskId) {
		securityUtil.logInAs(user);
		// user????????????ROLE_ACTIVITI_ADMIN
		// ??????legacy, ???????????????????????????
		org.activiti.api.task.model.Task completed = taskAdminRuntime.complete(
				TaskPayloadBuilder.complete().withTaskId(taskId).withVariable(nowStr(), "completeAdmin").build());
		return this.adminToMap(completed);
	}

	@GetMapping("/claimLegacy")
	public void claimLegacy(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		// ??????claim??????assignee?????????
		// method???????????????
		taskService.claim(taskId, user);
	}

	@GetMapping("/claim")
	public Map<String, Object> claim(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		securityUtil.logInAs(user);
		// user????????????ROLE_ACTIVITI_USER
		// candidateGroup?????????user?????????GROUP_[XXX]??????????????????claim, ??????claim
		org.activiti.api.task.model.Task claimed = taskRuntime
				.claim(TaskPayloadBuilder.claim().withTaskId(taskId).withAssignee(user).build());
		return this.toMap(claimed);
	}

	@GetMapping("/claimAdmin")
	public Map<String, Object> claimAdmin(@RequestParam(defaultValue = "gina") String user,
			@RequestParam String taskId) {
		securityUtil.logInAs(user);
		// user????????????ROLE_ACTIVITI_ADMIN
		// ??????legacy, ???claim????????????????????????
		org.activiti.api.task.model.Task claimed = taskAdminRuntime
				.claim(TaskPayloadBuilder.claim().withTaskId(taskId).withAssignee(user).build());
		return this.adminToMap(claimed);
	}

}

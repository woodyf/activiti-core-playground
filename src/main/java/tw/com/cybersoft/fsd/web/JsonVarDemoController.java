package tw.com.cybersoft.fsd.web;

import static tw.com.cybersoft.fsd.logic.Utils.nowStr;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.com.cybersoft.fsd.logic.DemoVariables;
import tw.com.cybersoft.fsd.logic.Utils;

@RestController
@RequestMapping("/jsonVar")
public class JsonVarDemoController {
	// 6
	private RuntimeService runtimeService;
	private TaskService taskService;

	public JsonVarDemoController(RuntimeService runtimeService, TaskService taskService) {
		this.runtimeService = runtimeService;
		this.taskService = taskService;
	}

	@GetMapping("/start")
	public String start(@RequestParam(defaultValue = "tommy") String user) {
		Authentication.setAuthenticatedUserId(user);
		DemoVariables demoVariables = new DemoVariables();
		demoVariables.setVar0("demo");
		org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("jsonVarTest", Map.of("jsonVar", demoVariables, "plainVar", "plainValue"));
		return processInstance.getId();
	}

	@GetMapping("/find")
	public List<Object> find(@RequestParam(defaultValue = "tommy") String user) {
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

	@GetMapping("/findWithCondition")
	public List<Object> findWithCondition(@RequestParam(defaultValue = "tommy") String user,
			@RequestParam(defaultValue = "plainVar") String key,
			@RequestParam(defaultValue = "plainValue") String value) {
		return taskService.createTaskQuery().taskCandidateOrAssigned(user).includeProcessVariables()
				.processVariableValueEquals(key, value).list().stream().map(this::toMap).collect(Collectors.toList());
	}

	@GetMapping("/complete")
	public void complete(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		DemoVariables demoVariables = new DemoVariables();
		demoVariables.setVar1(nowStr());
		taskService.complete(taskId, Collections.singletonMap("jsonVar", demoVariables));
	}

}

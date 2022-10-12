package tw.com.cybersoft.fsd.web;

import static tw.com.cybersoft.fsd.logic.Utils.nowStr;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntityImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timer")
public class TimerDemoController {
	// 6
	private RuntimeService runtimeService;
	private TaskService taskService;
	private HistoryService historyService;

	public TimerDemoController(RuntimeService runtimeService, TaskService taskService, HistoryService historyService) {
		this.runtimeService = runtimeService;
		this.taskService = taskService;
		this.historyService = historyService;
	}

	@GetMapping("/start")
	public String start(@RequestParam(defaultValue = "tommy") String user) {
		Authentication.setAuthenticatedUserId(user);
		org.activiti.engine.runtime.ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("timerTest", Collections.singletonMap(nowStr(), "timerStart"));
		return processInstance.getId();
	}

	@GetMapping("/findTask")
	public List<Object> findTask(@RequestParam(defaultValue = "tommy") String user) {
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

	@GetMapping("/complete")
	public void complete(@RequestParam(defaultValue = "tommy") String user, @RequestParam String taskId) {
		taskService.complete(taskId, Collections.singletonMap(nowStr(), "completeTask"));
	}

	@GetMapping("/findProcess")
	public List<Object> findProcess(@RequestParam(defaultValue = "gina") String user) {
		return runtimeService.createProcessInstanceQuery().includeProcessVariables().processDefinitionKey("timerTest")
				.list().stream().map(this::toMap).collect(Collectors.toList());
	}

	private Map<String, Object> toMap(org.activiti.engine.runtime.ProcessInstance process) {
		Object persistentState = ((ExecutionEntityImpl) process).getPersistentState();
		Map<String, Object> map = (Map<String, Object>) persistentState;
		map.put("processVariables", process.getProcessVariables());
		map.put("id", process.getId());
		return map;
	}

	@GetMapping("/findHistoryProcess")
	public List<Object> findHistoryProcess(@RequestParam(defaultValue = "gina") String user) {
		return historyService.createHistoricProcessInstanceQuery().includeProcessVariables()
				.processDefinitionKey("timerTest").list().stream().map(this::toMap).collect(Collectors.toList());
	}

	private Map<String, Object> toMap(org.activiti.engine.history.HistoricProcessInstance historyProcess) {
		Object persistentState = ((HistoricProcessInstanceEntityImpl) historyProcess).getPersistentState();
		Map<String, Object> map = (Map<String, Object>) persistentState;
		map.put("processVariables", historyProcess.getProcessVariables());
		map.put("id", historyProcess.getId());
		return map;
	}
}

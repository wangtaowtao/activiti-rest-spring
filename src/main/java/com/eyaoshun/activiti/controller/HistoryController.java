package com.eyaoshun.activiti.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eyaoshun.activiti.common.utils.ActivitiHttps;
import com.eyaoshun.activiti.common.utils.ActivitiRestConstant;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/history")
public class HistoryController {

	public static String PRE_URL = ActivitiRestConstant.SERVER_URL + ActivitiRestConstant.CONTEXT_PATH;

	@RequestMapping(value = "/activity", method = RequestMethod.GET)
	@ResponseBody
	public Object list(String activityId, String activityInstanceId, String activityName, String executionId,
			String finished, String taskAssignee, String processInstanceId, String processDefinitionId)
			throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(activityId)) {
			map.put("activityId", activityId);
		}
		if (StringUtils.isNotBlank(activityInstanceId)) {
			map.put("activityInstanceId", activityInstanceId);
		}
		if (StringUtils.isNotBlank(activityName)) {
			map.put("activityName", activityName);
		}
		if (StringUtils.isNotBlank(executionId)) {
			map.put("executionId", executionId);
		}
		if (StringUtils.isNotBlank(taskAssignee)) {
			map.put("taskAssignee", taskAssignee);
		}
		if (StringUtils.isNotBlank(processInstanceId)) {
			map.put("processInstanceId", processInstanceId);
		}
		if (StringUtils.isNotBlank(processDefinitionId)) {
			map.put("processDefinitionId", processDefinitionId);
		}
		if (finished != null) {
			map.put("finished", finished + "");
		}
		String url = PRE_URL + ActivitiRestConstant.HISTORY_ACTIVITY;
		return ActivitiHttps.doGet(url, map);
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	public Object detail(String id, String processInstanceId, String executionId, String activityInstanceId,
			String taskId) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(id)) {
			map.put("id", id);
		}
		if (StringUtils.isNotBlank(processInstanceId)) {
			map.put("processInstanceId", processInstanceId);
		}
		if (StringUtils.isNotBlank(executionId)) {
			map.put("executionId", executionId);
		}
		if (StringUtils.isNotBlank(activityInstanceId)) {
			map.put("activityInstanceId", activityInstanceId);
		}
		if (StringUtils.isNotBlank(taskId)) {
			map.put("taskId", taskId);
		}
		String url = PRE_URL + ActivitiRestConstant.HISTORY_DETAIL;
		return ActivitiHttps.doGet(url, map);
	}

	@RequestMapping(value = "/detail_id", method = RequestMethod.GET)
	@ResponseBody
	public Object detail(String id) throws Exception {
		String url = PRE_URL + ActivitiRestConstant.HISTORY_DETAIL + "/" + id + "/data";
		return ActivitiHttps.doGet(url);
	}

	@RequestMapping(value = "/process_instances", method = RequestMethod.GET)
	@ResponseBody
	public Object processInstances(String processInstanceId, String processDefinitionKey, 
			String processDefinitionId, String businessKey,
			String finished) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(processInstanceId)) {
			map.put("processInstanceId", processInstanceId);
		}
		if (StringUtils.isNotBlank(processDefinitionKey)) {
			map.put("processDefinitionKey", processDefinitionKey);
		}
		if (StringUtils.isNotBlank(processDefinitionId)) {
			map.put("processDefinitionId", processDefinitionId);
		}
		if (StringUtils.isNotBlank(businessKey)) {
			map.put("businessKey", businessKey);
		}
		if (finished != null) {
			map.put("finished", finished+"");
		}

		String url = PRE_URL + ActivitiRestConstant.HISTORY_PROCESS_INSTANCES;
		return ActivitiHttps.doGet(url, map);
	}
	
	@RequestMapping(value = "/get_process_instance", method = RequestMethod.GET)
	@ResponseBody
	public Object getProcessInstance(String id) throws Exception {
		String url = PRE_URL + ActivitiRestConstant.HISTORY_PROCESS_INSTANCES + "/" + id;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value = "/process_instance_commons", method = RequestMethod.GET)
	@ResponseBody
	public Object processInstanceCommons(String id) throws Exception {
		String url = PRE_URL + ActivitiRestConstant.HISTORY_PROCESS_INSTANCES + "/" + id + "/comments";
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value = "/process_instance_common", method = RequestMethod.GET)
	@ResponseBody
	public Object processInstanceCommon(String id, String commentId) throws Exception {
		String url = PRE_URL + ActivitiRestConstant.HISTORY_PROCESS_INSTANCES + "/" + id + "/comments/" + commentId;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value = "/tasks", method = RequestMethod.GET)
	@ResponseBody
	public Object tasks(String taskId, 
			String processInstanceId,
			String processDefinitionKey,
			String processDefinitionId,
			String processDefinitionName,
			String processBusinessKey,
			String executionId,
			String taskDefinitionKey,
			String taskName,
			String taskAssignee,
			String finished,
			String processFinished) throws Exception {
		
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(taskId)) 
			map.put("taskId", taskId);
		if (StringUtils.isNotBlank(processInstanceId)) 
			map.put("processInstanceId", processInstanceId);
		if (StringUtils.isNotBlank(processDefinitionKey)) 
			map.put("processDefinitionKey", processDefinitionKey);
		if (StringUtils.isNotBlank(processDefinitionId)) 
			map.put("processDefinitionId", processDefinitionId);
		if (StringUtils.isNotBlank(processDefinitionName)) 
			map.put("processDefinitionName", processDefinitionName);
		if (StringUtils.isNotBlank(processBusinessKey)) 
			map.put("processBusinessKey", processBusinessKey);
		if (StringUtils.isNotBlank(executionId)) 
			map.put("executionId", executionId);
		if (StringUtils.isNotBlank(taskDefinitionKey)) 
			map.put("taskDefinitionKey", taskDefinitionKey);
		if (StringUtils.isNotBlank(taskName)) 
			map.put("taskName", taskName);
		if (StringUtils.isNotBlank(taskAssignee)) 
			map.put("taskAssignee", taskAssignee);
		if (StringUtils.isNotBlank(finished)) 
			map.put("finished", finished);
		if (StringUtils.isNotBlank(processFinished)) 
			map.put("processFinished", processFinished);

		String url = PRE_URL + ActivitiRestConstant.TASKS;
		return ActivitiHttps.doGet(url, map);
	}
	
	@RequestMapping(value = "/task", method = RequestMethod.GET)
	@ResponseBody
	public Object task(String id) throws Exception {
		
		String url = PRE_URL + ActivitiRestConstant.TASKS + "/" + id;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value = "/variable", method = RequestMethod.GET)
	@ResponseBody
	public Object variable(String processInstanceId, String taskId, String variableName) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(processInstanceId)) 
			map.put("processInstanceId", processInstanceId);
		if (StringUtils.isNotBlank(taskId)) 
			map.put("taskId", taskId);
		if (StringUtils.isNotBlank(variableName)) 
			map.put("variableName", variableName);
		String url = PRE_URL + ActivitiRestConstant.HISTORY_VARIABLE_INSTANCES;
		return ActivitiHttps.doGet(url, map);
	}


}

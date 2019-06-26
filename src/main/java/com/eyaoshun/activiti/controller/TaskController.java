package com.eyaoshun.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eyaoshun.activiti.common.utils.ActivitiHttps;
import com.eyaoshun.activiti.common.utils.ActivitiRestConstant;
import com.fasterxml.jackson.core.JsonParser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Booleans;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	public static String PRE_URL = ActivitiRestConstant.SERVER_URL + ActivitiRestConstant.CONTEXT_PATH + ActivitiRestConstant.TASKS;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
	public Object list(String name, String assignee, String processInstanceId,
			String executionId,
			String processDefinitionKey,
			boolean active,
			String processInstanceBusinessKey) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(name)) {
			map.put("name", name);
		}
		if (StringUtils.isNoneBlank(assignee)) {
			map.put("assignee", assignee);
		}
		if (StringUtils.isNoneBlank(processInstanceId)) {
			map.put("processInstanceId", processInstanceId);
		}
		if (StringUtils.isNoneBlank(executionId)) {
			map.put("executionId", executionId);
		}
		if (StringUtils.isNoneBlank(processDefinitionKey)) {
			map.put("processDefinitionKey", processDefinitionKey);
		}
		if (active) {
			map.put("active", Boolean.toString(active));
		}
		if (StringUtils.isNoneBlank(processInstanceBusinessKey)) {
			map.put("processInstanceBusinessKey", processInstanceBusinessKey);
		}
		String url = PRE_URL;
		return ActivitiHttps.doGet(url, map);
	}
	
	@RequestMapping(value="/get_task", method=RequestMethod.GET)
	@ResponseBody
	public Object getTask(String id) throws Exception {
		String url = PRE_URL + "/" + id;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/action_task", method=RequestMethod.GET)
	@ResponseBody
	public Object actionTask(String id, String action, String assignee, String name, String type, Object value) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("name", name);
		map.put("type", type);
		map.put("value", value);

		Map<String, Object> varMap = Maps.newHashMap();
		varMap.put("action", action);
		if (StringUtils.isNoneBlank(name)) {
			varMap.put("variables", Lists.newArrayList(map));
		}
		if (StringUtils.isNoneBlank(assignee)) {
			varMap.put("assignee", assignee);
		}
		String jsonStr = JSONObject.toJSONString(varMap);
		String url = PRE_URL + "/" + id;
		return ActivitiHttps.postJson(url, jsonStr);
	}
	
	@RequestMapping(value="/get_comments", method=RequestMethod.GET)
	@ResponseBody
	public Object getComments(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/comments";
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/create_comments", method=RequestMethod.GET)
	@ResponseBody
	public Object createComments(String id, String author, String message, String url, String type, Boolean saveProcessInstanceId) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("author", author);
		map.put("message", message);
		map.put("url", url);
		map.put("type", type);
		map.put("author", author);
		map.put("saveProcessInstanceId", saveProcessInstanceId);
		
		String jsonStr = JSONObject.toJSONString(map);

		String questurl = PRE_URL + "/" + id + "/comments";
		return ActivitiHttps.postJson(questurl, jsonStr);
	}
	
	@RequestMapping(value="/delete_comments", method=RequestMethod.GET)
	@ResponseBody
	public Object deleteComments(String id, String commentId) throws Exception {
		String url = PRE_URL + "/" + id + "/comments/" + commentId;
		return ActivitiHttps.doDelete(url);
	}
	
	@RequestMapping(value="/get_comment", method=RequestMethod.GET)
	@ResponseBody
	public Object deleteComment(String id, String commentId) throws Exception {
		String url = PRE_URL + "/" + id + "/comments/" + commentId;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/get_events", method=RequestMethod.GET)
	@ResponseBody
	public Object getEvents(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/events/";
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/get_event", method=RequestMethod.GET)
	@ResponseBody
	public Object getEvent(String id, String eventId) throws Exception {
		String url = PRE_URL + "/" + id + "/events/" + eventId;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/delete_event", method=RequestMethod.GET)
	@ResponseBody
	public Object getEvents(String id, String eventId) throws Exception {
		String url = PRE_URL + "/" + id + "/events/" + eventId;
		return ActivitiHttps.doDelete(url);
	}
	
	@RequestMapping(value="/get_subtasks", method=RequestMethod.GET)
	@ResponseBody
	public Object getSubtasks(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/subtasks";
		return ActivitiHttps.doGet(url);
	}
	@RequestMapping(value="/delete_subtasks", method=RequestMethod.GET)
	@ResponseBody
	public Object deleteSubtasks(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/subtasks";
		return ActivitiHttps.doDelete(url);
	}
	
	@RequestMapping(value="/delete_variables", method=RequestMethod.GET)
	@ResponseBody
	public Object deleteVariables(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/variables";
		return ActivitiHttps.doDelete(url);
	}
	
	@RequestMapping(value="/get_variables", method=RequestMethod.GET)
	@ResponseBody
	public Object getVariables(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/variables";
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/add_variables", method=RequestMethod.GET)
	@ResponseBody
	public Object addVariables(String id,  List<String> names, List<String> types, List<Object> values) throws Exception {
		List<Map<String, Object>> varList = Lists.newArrayList();
		for (int i = 0; i < names.size(); i++) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("name", names.get(i));
			map.put("type", types.get(i));
			map.put("value", values.get(i));
			varList.add(map);
		}
		String jsonStr = JSONObject.toJSONString(varList);
		
		String url = PRE_URL + "/" + id + "/variables";
		return ActivitiHttps.postJson(url, jsonStr);
	}
	
	@RequestMapping(value="/delete_variables_name", method=RequestMethod.GET)
	@ResponseBody
	public Object deleteVariablesName(String id, String name) throws Exception {
		String url = PRE_URL + "/" + id + "/variables" + "/" + name;
		return ActivitiHttps.doDelete(url);
	}
	
	@RequestMapping(value="/get_variables_name", method=RequestMethod.GET)
	@ResponseBody
	public Object getVariablesName(String id, String name) throws Exception {
		String url = PRE_URL + "/" + id + "/variables" + "/" + name;
		return ActivitiHttps.doGet(url);
	}
	
	
	@RequestMapping(value="/update_variables_name", method=RequestMethod.GET)
	@ResponseBody
	public Object updateVariablesName(String id, String name, String type, String value, String valueUrl, String scope) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		map.put("name", name);
		map.put("type", type);
		map.put("value", value);
		map.put("valueUrl", valueUrl);
		map.put("scope", scope);
		String jsonStr = JSONObject.toJSONString(map);
		
		String url = PRE_URL + "/" + id + "/variables" + "/" + name;
		return ActivitiHttps.putJson(url, jsonStr);
	}
	
	@RequestMapping(value="/get_variables_name_data", method=RequestMethod.GET)
	@ResponseBody
	public Object getVariablesNameData(String id, String name) throws Exception {
		String url = PRE_URL + "/" + id + "/variables" + "/" + name + "/data";
		return ActivitiHttps.doGet(url);
	}
}

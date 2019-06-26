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

@Controller
@RequestMapping("/instances")
public class ProcessInstancesController {

	public static String PRE_URL = ActivitiRestConstant.SERVER_URL + ActivitiRestConstant.CONTEXT_PATH + ActivitiRestConstant.PROCESS_INSTANCES;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
	public Object list(String id, String processDefinitionKey, String processDefinitionId, String businessKey) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(id)) {
			map.put("id", id);
		}
		if (StringUtils.isNoneBlank(processDefinitionKey)) {
			map.put("processDefinitionKey", processDefinitionKey);
		}
		if (StringUtils.isNoneBlank(processDefinitionId)) {
			map.put("processDefinitionId", processDefinitionId);
		}
		if (StringUtils.isNoneBlank(businessKey)) {
			map.put("businessKey", businessKey);
		}
		String url = PRE_URL;
		return ActivitiHttps.doGet(url, map);
	}
	
	@RequestMapping(value="/start", method=RequestMethod.GET)
	@ResponseBody
	public Object start(String processDefinitionKey, String processDefinitionId, String businessKey, String name, String value) throws Exception {
		Map<String, Object> entityMap = Maps.newHashMap();
		if (StringUtils.isNoneBlank(processDefinitionKey)) 
			entityMap.put("processDefinitionKey", processDefinitionKey);
		else 
			entityMap.put("processDefinitionId", processDefinitionId);
		
		if (StringUtils.isNoneBlank(businessKey)) 
			entityMap.put("businessKey", businessKey);
		
		if (StringUtils.isNoneBlank(name)) {
			Map<String, String> varMap = Maps.newHashMap();
			varMap.put("name", name);
			varMap.put("value", value);
			List<Map<String, String>> list = Lists.newArrayList(varMap);
			entityMap.put("variables", list);
		}
		String url = PRE_URL;
		String jsonStr = JSONObject.toJSONString(entityMap);
		return ActivitiHttps.postJson(url, jsonStr);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	@ResponseBody
	public Object delete(String id) throws Exception {
		String url = PRE_URL + "/" + id;
		return ActivitiHttps.doDelete(url);
	}
	
	@RequestMapping(value="/get_instance", method=RequestMethod.GET)
	@ResponseBody
	public Object getInstance(String id) throws Exception {
		String url = PRE_URL + "/" + id;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/act_or_sus", method=RequestMethod.GET)
	@ResponseBody
	public Object actOrSus(String id, Integer action) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (action == 0) {
			map.put("action", "suspend");
		}else {
			map.put("action", "activate");
		}
		String url = PRE_URL + "/" + id;
		return ActivitiHttps.putJson(url, JSONObject.toJSONString(map));
	}
	
	@RequestMapping(value="/get_diagram", method=RequestMethod.GET)
	@ResponseBody
	public Object getDiagram(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/get_diagram";
		return ActivitiHttps.doGet(url);
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
	
	@RequestMapping(value="/update_variables", method=RequestMethod.GET)
	@ResponseBody
	public Object updateVariables(String id,  List<String> names, List<String> types, List<Object> values) throws Exception {
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
		return ActivitiHttps.putJson(url, jsonStr);
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

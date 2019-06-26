package com.eyaoshun.activiti.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.eyaoshun.activiti.common.utils.ActivitiHttps;
import com.eyaoshun.activiti.common.utils.ActivitiRestConstant;
import com.fasterxml.jackson.core.JsonParser;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/definitions")
public class ProcessDefinitionsController {

	public static String PRE_URL = ActivitiRestConstant.SERVER_URL + ActivitiRestConstant.CONTEXT_PATH + ActivitiRestConstant.PROCESS_DEFINITIONS;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
	public Object list(String name, String key) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(name)) {
			map.put("nameLike", name);
		}
		if (StringUtils.isNoneBlank(key)) {
			map.put("keyLike", key);
		}
		String url = PRE_URL;
		return ActivitiHttps.doGet(url, map);
	}
	
	@RequestMapping(value="/get_definitions", method=RequestMethod.GET)
	@ResponseBody
	public Object getDeployments(String id) throws Exception {
		String url = PRE_URL + "/" + id;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/get_definitions_resourcedata", method=RequestMethod.GET)
	@ResponseBody
	public Object getDeploymentsResources(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/" + "resourcedata";
		return ActivitiHttps.doGet(url);
	}
	
}

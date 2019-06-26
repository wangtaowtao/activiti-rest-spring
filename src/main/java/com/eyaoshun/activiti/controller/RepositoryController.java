package com.eyaoshun.activiti.controller;

import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.eyaoshun.activiti.common.utils.ActivitiHttps;
import com.eyaoshun.activiti.common.utils.ActivitiRestConstant;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/repository")
public class RepositoryController {

	public static String PRE_URL = ActivitiRestConstant.SERVER_URL + ActivitiRestConstant.CONTEXT_PATH + ActivitiRestConstant.REPOSITORY_DEPLOYMENTS;
	
	@RequestMapping(value="/create", method=RequestMethod.POST)
	@ResponseBody
	public Object saveRepository(@RequestParam("file") CommonsMultipartFile file) throws Exception {
		String url = PRE_URL;
		return ActivitiHttps.postMultipartEntity(url, file) ;
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
	public Object listOfDeployments(String name) throws Exception {
		Map<String, String> map = Maps.newHashMap();
		if (StringUtils.isNotBlank(name)) {
			map.put("nameLike", name);
		}
		String url = PRE_URL;
		return ActivitiHttps.doGet(url, map);
	}
	
	@RequestMapping(value="/get_deployments", method=RequestMethod.GET)
	@ResponseBody
	public Object getDeployments(String id) throws Exception {
		String url = PRE_URL + "/" + id;
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/get_deployments_resources", method=RequestMethod.GET)
	@ResponseBody
	public Object getDeploymentsResources(String id) throws Exception {
		String url = PRE_URL + "/" + id + "/" + "resources";
		return ActivitiHttps.doGet(url);
	}
	
	@RequestMapping(value="/get_deployments_resourcedata", method=RequestMethod.GET)
	public void getDeploymentsResources(String id, String resourceId, HttpServletResponse response) throws Exception {
		String url = PRE_URL + "/" + id + "/" + "resourcedata" + "/" + resourceId;
		InputStream in = ActivitiHttps.getHttpInputStream(url, null , null);
		byte[] b = new byte[1024];
        int len = -1;
        while ((len = in.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	@ResponseBody
	public Object deleteDeployments(String id) throws Exception {
		String url = PRE_URL + "/" + id;
		return ActivitiHttps.doDelete(url);
	}
}

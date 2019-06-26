package com.eyaoshun.activiti.common.utils;

public class ActivitiRestConstant {

	public static String SERVER_URL = "http://localhost:8888";
	// public static String CONTEXT_PATH = "/activiti-rest/service/";
	public static String CONTEXT_PATH = "/";
	
	// 部署信息
	public static String REPOSITORY_DEPLOYMENTS = "repository/deployments";
	// 运行信息
	public static String RUNTIME_EXECUTIONS = "runtime/executions";
	// 流程定义
	public static String PROCESS_DEFINITIONS  = "repository/process-definitions";
	// 流程实例
	public static String PROCESS_INSTANCES  = "runtime/process-instances";
	// 任务
	public static String TASKS  = "runtime/tasks";
	
	// 历史活动实例
	public static String HISTORY_ACTIVITY = "history/historic-activity-instances";
	// 历史详情
	public static String HISTORY_DETAIL = "history/historic-detail";
	// 历史流程实例
	public static String HISTORY_PROCESS_INSTANCES = "history/historic-process-instances";
	// 历史任务实例
	public static String HISTORY_TASK_INSTANCES = "history/historic-task-instances";
	// 历史变量实例
	public static String HISTORY_VARIABLE_INSTANCES = "history/historic-variable-instances";
	
}

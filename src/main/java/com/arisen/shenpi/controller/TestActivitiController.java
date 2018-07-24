package com.arisen.shenpi.controller;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/processes")
public class TestActivitiController {
	@Autowired
	private ProcessEngine processEngine;		 //流程引擎对象
	
	@Autowired
	private RepositoryService repositoryService; //管理流程定义  与流程定义和部署对象相关的Service
	
	@Autowired
	private RuntimeService runtimeService; 		//与正在执行的流程实例和执行对象相关的Service(执行管理，包括启动、推进、删除流程实例等操作)
	
	@Autowired
	private TaskService taskService; 			//任务管理 与正在执行的任务管理相关的Service
	
	@Autowired
	private HistoryService historyService; 		//历史管理(执行完的数据的管理)
	
	
	@RequestMapping(value="/list")
	public List<Deployment> getAllDefProcess() {
		//processEngine.getProcessEngineConfiguration().get
		List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
		//repositoryService.activateProcessDefinitionByKey("KEY_zhaobiao");
	  runtimeService.addParticipantUser(processInstanceId, userId);startProcessInstanceByKey(arg0, arg1)startProcessInstanceByKey("KEY_zhaobiao");
		return deployments;
	}
	
	
}

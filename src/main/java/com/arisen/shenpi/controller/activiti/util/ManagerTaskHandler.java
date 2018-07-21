package com.arisen.shenpi.controller.activiti.util;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;


/** 
 * 名称：指定下一任务待办人
 * 创建人：FH Admin fh313596790qq(青苔)
 * 更新时间：2018年2月4日
 * @version
 */
@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
			throw new RuntimeException("not implemented");
	}

}

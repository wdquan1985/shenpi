package com.arisen.shenpi.service.activiti;

import java.util.List;

import com.arisen.shenpi.model.Page;
import com.arisen.shenpi.util.PageData;

/** 
 * 说明： 流程管理接口
 * 创建人：FH Q313596790
 * 创建时间：2018-01-06
 * @version
 */
public interface ProcdefManager{

	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
}


package com.arisen.shenpi.service.activiti;

import java.util.List;

import com.arisen.shenpi.model.Page;
import com.arisen.shenpi.util.PageData;


/** 
 * 说明： 历史流程任务接口
 * 创建人：FH Q313596790
 * 创建时间：2018-01-28
 * @version
 */
public interface HiprocdefManager{

	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**历史流程变量列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> hivarList(PageData pd)throws Exception;
	
}


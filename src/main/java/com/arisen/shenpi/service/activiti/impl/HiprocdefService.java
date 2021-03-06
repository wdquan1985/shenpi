package com.arisen.shenpi.service.activiti.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.arisen.shenpi.dao.DaoSupport;
import com.arisen.shenpi.model.Page;
import com.arisen.shenpi.service.activiti.HiprocdefManager;
import com.arisen.shenpi.util.PageData;

/** 
 * 说明： 历史流程任务
 * 创建人：FH Q313596790
 * 创建时间：2018-01-28
 * @version
 */
@Service("hiprocdefService")
public class HiprocdefService implements HiprocdefManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("HiprocdefMapper.datalistPage", page);
	}
	
	/**历史流程变量列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hivarList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("HiprocdefMapper.hivarList", pd);
	}
}


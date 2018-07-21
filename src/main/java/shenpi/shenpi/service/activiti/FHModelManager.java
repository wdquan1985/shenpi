package shenpi.shenpi.service.activiti;

import java.util.List;

import shenpi.shenpi.model.Page;
import shenpi.shenpi.util.PageData;


/** 
 * 说明： 工作流模型管理接口
 * 创建人：FH Q313596790
 * 创建时间：2017-12-26
 * @version
 */
public interface FHModelManager{

	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
}


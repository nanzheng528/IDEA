package com.freedoonline.domain;

import java.util.List;

import com.freedoonline.service.bo.MsgTemplateQueryBo;

import cn.cloudlink.core.framework.domain.entity.MsgTemplate;

/**
 *<p>类描述：短信服务SMS数据操作接口。</p>
 * @version v1.0.0.1。
 * @since JDK1.8。
 *<p>创建日期：2017年4月15日 下午5:46:04。</p>
*/
public interface IMsmDao {

	/**
	  * <p>功能描述：根据条件查询模板。</p>
	  * <p> 赵杨                                           [zhaoyang]。</p>	
	  * @param queryBo：
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2017年4月15日 下午2:41:20。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public List<MsgTemplate> queryListMsgTemplate(MsgTemplateQueryBo queryBo);
}

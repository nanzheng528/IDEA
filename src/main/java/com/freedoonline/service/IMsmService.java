package com.freedoonline.service;

import java.util.Map;

import cn.cloudlink.core.common.exception.BusinessException;
/**
  *<p>类描述：短信服务SMS业务接口。</p>
  * @version v1.0.0.1。
  * @since JDK1.8。
  *<p>创建日期：2016年11月16日 下午5:46:04。</p>
 */
public interface IMsmService {

	
	/**
	  * <p>功能描述：。</p>
	  * @param serverMode
	  * @param paramMap
	  * @return
	  * @throws Exception
	  * @since JDK1.8。
	  * <p>创建日期:2017年4月15日 下午1:44:27。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	public Object sendMsm(Map<String,Object> paramMap)throws BusinessException,Exception;
}

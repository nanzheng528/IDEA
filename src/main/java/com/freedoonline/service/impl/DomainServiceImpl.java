package com.freedoonline.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.domain.DomainDao;
import com.freedoonline.service.DomainService;

import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service("domainServiceImpl")
public class DomainServiceImpl implements DomainService {
	
	@Resource(name = "domainDaoImpl")
	private DomainDao domainDao;
	
	/**
	  * 
	  * <p>功能描述:获取阈值列表。</p>	
	  * @param paramMap
	  * @return
	  * @throws BusinessException
	  * @throws Exception
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年6月1日 下午3:24:17。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object queryThreshold(Map<String, Object> paramMap) throws BusinessException, Exception {
		// TODO Auto-generated method stub
		String domainName = (String)paramMap.get("domainName");
		if(!StringUtil.hasText(domainName)){
			throw new BusinessException("阈值类型不能为空！", "403");
		}
		return domainDao.queryThreshold(paramMap);
	}
}

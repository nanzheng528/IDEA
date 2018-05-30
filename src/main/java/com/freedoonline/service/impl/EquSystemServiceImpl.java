package com.freedoonline.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.domain.EquSystemDao;
import com.freedoonline.domain.entity.EquSystem;
import com.freedoonline.service.EquSystemService;

import cn.cloudlink.core.common.exception.BusinessException;

@Service("equSystemServiceImpl")
public class EquSystemServiceImpl implements EquSystemService{
	
	@Resource(name = "equSystemDaoImpl")
	private EquSystemDao equSystemDao;
	
	/**
	  * 
	  * <p>功能描述:根据企业获取主系统列表。</p>	
	  * @param system
	  * @return
	  * @throws BusinessException
	  * @throws Exception
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年5月12日 上午10:47:48。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object querySystemList(EquSystem system) throws BusinessException, Exception {
		return equSystemDao.querySystemList(system);
	}
}

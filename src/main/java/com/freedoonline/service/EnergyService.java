package com.freedoonline.service;

import java.util.Map;

import com.freedoonline.domain.entity.Electricity;

import cn.cloudlink.core.common.exception.BusinessException;

public interface EnergyService {
	
	public Object waterConsumption(Map<String, Object> param) throws BusinessException,Exception;
	
	public Object eleAdd(Electricity electricity) throws BusinessException,Exception;
	
	public String eleUpdate(Map<String,Object> paramMap) throws BusinessException,Exception;
	
	public Object eleQuery(Map<String, Object> param) throws BusinessException,Exception;
}

package com.freedoonline.service;

import java.util.Map;

import com.freedoonline.domain.entity.Meter;

import cn.cloudlink.core.common.exception.BusinessException;

public interface MeterService {
	
	public String addMeter(Meter meter);
	
	public Boolean delMeter(Map<String, Object> map) throws BusinessException,Exception;
	
	public int validMeterId(String objectId) throws BusinessException,Exception;
	
	public String updateMeter(Meter meter) throws BusinessException,Exception;
}

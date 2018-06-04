package com.freedoonline.service;

import java.util.Map;

import com.freedoonline.domain.entity.Meter;
import com.freedoonline.service.bo.MeterBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

public interface MeterService {
	
	public String addMeter(Meter meter);
	
	public Boolean delMeter(Map<String, Object> map) throws BusinessException,Exception;
	
	public int validMeterId(String objectId) throws BusinessException,Exception;
	
	public Boolean updateMeter(Meter meter) throws BusinessException,Exception;
	
	public Page<Meter> queryMeter(PageRequest pageRequest,MeterBo meter);
}

package com.freedoonline.domain;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Meter;
import com.freedoonline.service.bo.MeterBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;

public interface MeterDao {
	public String addMeter(Meter meter);
	
	public int delMeter(Map<String, Object> map);
	
	public int updateMeter(Meter meter);
	
	public Page<Meter> queryMeterData(PageRequest pageRequest,MeterBo meter); 
	
	public List queryPosition(Map<String,Object> searchMap);
	
}

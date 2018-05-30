package com.freedoonline.domain;

import java.util.Map;

import com.freedoonline.domain.entity.Electricity;

public interface EnergyDao {

	public Object waterConsumption(Map<String, Object> param);
	
	public Object eleAdd(Electricity electricity);
	
	public Electricity queryEleById(String objectId,String enpId);
	//
	public Object eleQuery(Map<String, Object> param);
	
}

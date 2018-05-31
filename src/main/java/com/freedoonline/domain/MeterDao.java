package com.freedoonline.domain;

import java.util.Map;

import com.freedoonline.domain.entity.Meter;

public interface MeterDao {
	public String addMeter(Meter meter);
	
	public int delMeter(Map<String, Object> map);
}

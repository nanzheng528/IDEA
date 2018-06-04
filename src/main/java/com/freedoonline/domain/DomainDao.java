package com.freedoonline.domain;

import java.util.Map;

public interface DomainDao {
	
	//获取阈值列表
	Object queryThreshold(Map<String, Object> paramMap);
}

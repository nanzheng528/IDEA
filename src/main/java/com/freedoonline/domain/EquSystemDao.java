package com.freedoonline.domain;

import com.freedoonline.domain.entity.EquSystem;

public interface EquSystemDao {
	
	// 获取系统列表
	public Object querySystemList(EquSystem system);
}

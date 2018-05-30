package com.freedoonline.service;

import com.freedoonline.domain.entity.EquSystem;

import cn.cloudlink.core.common.exception.BusinessException;

public interface EquSystemService {
	
	// 获取系统列表
	public Object querySystemList(EquSystem system)throws BusinessException,Exception;
}

package com.freedoonline.service;

import java.util.Map;

import cn.cloudlink.core.common.exception.BusinessException;

public interface DomainService {
	
	// 获取阈值列表
	Object queryThreshold(Map<String, Object> paramMap) throws BusinessException,Exception;
}

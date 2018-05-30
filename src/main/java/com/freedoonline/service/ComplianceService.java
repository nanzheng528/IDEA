package com.freedoonline.service;

import java.util.Map;

import cn.cloudlink.core.common.exception.BusinessException;

public interface ComplianceService {
	
	// 合规性人员
	public Object maintenace(Map<String, Object> param) throws BusinessException,Exception;
	// 合规性楼宇
	public Object buildingLicense(Map<String, Object> param) throws BusinessException,Exception;
	// 合规性设备
	public Object equipmentAuth(Map<String, Object> param) throws BusinessException,Exception;
	// 消息推送
	public Object pushMsg(Map<String, Object> param) throws BusinessException,Exception;
	// 认证
	public Object auth(Map<String, Object> param) throws BusinessException,Exception;
	// 认证得分
    public Object authScore(Map<String, Object> param) throws BusinessException,Exception;

}

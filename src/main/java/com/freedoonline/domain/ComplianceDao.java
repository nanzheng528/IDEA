package com.freedoonline.domain;

import java.util.Map;

public interface ComplianceDao {
	// 合规性人员
	public Object maintenace(Map<String, Object> param);
	// 合规性人员
	public Object buildingLicense(Map<String, Object> param);
	// 合规性设备
	public Object equipmentAuth(Map<String, Object> param);
	// 消息推送
	public Object pushMsg(Map<String, Object> param);
	// 认证
	public Object auth(Map<String, Object> param,String type);
	
	public Object authEquipment(String authType,String buildingId);
	public Object authBuilding(String authType,String buildingId);
	public Object authUser(String authType,String buildingId);
	public Object authScore(Map<String, Object> param);
}

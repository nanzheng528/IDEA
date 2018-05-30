package com.freedoonline.domain;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Equipment;
import com.freedoonline.domain.entity.Hiecs;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.SubSystemFault;
import com.freedoonline.domain.entity.SystemFault;
import com.freedoonline.service.bo.EquipmentBo;
import com.freedoonline.service.bo.MaintenancePlanBo;
import com.freedoonline.service.bo.SubSystemBo;
import com.freedoonline.service.bo.SystemFaultPct;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

public interface EquipmentDao {
	
	public Page<Equipment>  queryListEquipment(PageRequest pageRequest,EquipmentBo queryBo);
	
	public Page<MaintenancePlan> queryListMaintenancePlan(PageRequest pageRequest,MaintenancePlanBo queryBo);
	
	public Object addEquipment(EquipmentBo queryBo);
	
	public Object addMaintenancePlan(MaintenancePlanBo queryBo);
	
	public List<SystemFault> systemFault(Map<String, Object> param);
	
	public List<SubSystemBo> querySub(String systemId, String enpId);
	
	public List<SubSystemFault> subSystemFault(Map<String, Object> param);
	
	public List<MaintenancePlan> equSystemPct(Map<String, Object> param);
	
	// 消息推送
	public Object pushMsg(Map<String, Object> param) throws BusinessException,Exception;
	
	public Object faultRate(Map<String, Object> param);
	// 根据ID查询设备
	public Equipment queryEquById(String objectId,String enpId);
	
	public Object totalSystemEqu(String buildingId);
	//获取维护人员列表
	public Object queryMaintenanceUser(Map<String,Object> paramMap);
}

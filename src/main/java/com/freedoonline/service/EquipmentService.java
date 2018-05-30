package com.freedoonline.service;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Equipment;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.SubSystemFault;
import com.freedoonline.domain.entity.SystemFault;
import com.freedoonline.service.bo.EquipmentBo;
import com.freedoonline.service.bo.MaintenancePlanBo;
import com.freedoonline.service.bo.SubSystemBo;
import com.freedoonline.service.bo.SystemFaultBo;
import com.freedoonline.service.bo.SystemFaultPct;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

public interface EquipmentService {
	
	public Page<Equipment> queryListEquipment(PageRequest pageRequest,EquipmentBo queryBo)throws BusinessException,Exception;
	
	public Page<MaintenancePlan> queryListMaintenancePlan(PageRequest pageRequest,MaintenancePlanBo queryBo)throws BusinessException,Exception;
	
	public Object addEquipment(EquipmentBo queryBo) throws BusinessException,Exception;
	
	public Object addMaintenancePlan(MaintenancePlanBo queryBo) throws BusinessException,Exception;
	
	public List<SystemFaultBo> systemFault(Map<String, Object> param) throws BusinessException,Exception;
	
	public List<SubSystemBo> querySub(String systemId,String enpId)throws BusinessException,Exception;
	
	public List<SubSystemFault> subSystemFault(Map<String, Object> param) throws BusinessException,Exception;
	
	public List<SystemFaultPct> equSystemPct(Map<String, Object> param) throws BusinessException,Exception;
	
	// 消息推送
	public Object pushMsg(Map<String, Object> param) throws BusinessException,Exception;
	//故障率
	public Object faultRate(Map<String, Object> param) throws BusinessException,Exception;
	//更新设备
	public String updateEquById(Map<String,Object> paramMap) throws BusinessException,Exception;
	//更新维护计划状态
	public Object updateMaintenancePlan(Map<String,Object> paramMap) throws BusinessException,Exception;
	// 获取维护人员列表
	public Object queryMaintenanceUser(Map<String,Object> paramMap) throws BusinessException,Exception;
}

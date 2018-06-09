package com.freedoonline.service;

import java.util.Map;

import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.bo.MaintenancePlanBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

public interface MpService {
	
	// 增加巡检计划
	public Object addMp(Map<String, Object> param,User user) throws BusinessException,Exception;
	// 获取巡检计划列表
	public Page<MaintenancePlan> queryListPlan(PageRequest pageRequest,MaintenancePlanBo queryBo,User user)throws BusinessException,Exception;
	// 获取统计图
	public Object stats(PageRequest pageRequest,MaintenancePlanBo queryBo,User user)throws BusinessException,Exception;
}

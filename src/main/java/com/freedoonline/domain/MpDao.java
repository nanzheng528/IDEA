package com.freedoonline.domain;

import java.util.Map;

import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.bo.MaintenancePlanBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;

public interface MpDao {

	//增加巡检计划
	public Object addMp(Map<String, Object> param,User user);
	//
	public Page<MaintenancePlan> queryListPlan(PageRequest pageRequest,MaintenancePlanBo queryBo,User user);
}

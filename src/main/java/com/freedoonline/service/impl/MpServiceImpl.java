package com.freedoonline.service.impl;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.domain.MpDao;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.MpService;
import com.freedoonline.service.bo.MaintenancePlanBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service("mpServiceImpl")
public class MpServiceImpl implements MpService {

	@Resource(name = "mpDaoImpl")
	private MpDao mpDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object addMp(Map<String, Object> param, User user) throws BusinessException, Exception {
		
		Long planStartTime = (Long) param.get("planStartTime");
		Long planEndTime = (Long) param.get("planEndTime");
		
		if (!StringUtil.hasText((String)param.get("name"))) {
			throw new BusinessException("巡检名称不能为空！", "403");
		}
		List <String> list = (List<String>)param.get("equId");
		if (list !=null && list.size() < 0 ) {
			throw new BusinessException("设备ID不能为空！", "403");
		}
		if (!StringUtil.hasText(((Integer)param.get("type")).toString())) {
			throw new BusinessException("巡检类型不能为空！", "403");
		}
		if (!StringUtil.hasText(((Integer)param.get("frequency")).toString())) {
			throw new BusinessException("巡检频率不能为空！", "403");
		}
		if (!StringUtil.hasText(((String)param.get("maintenanceId")))) {
			throw new BusinessException("巡检人员不能为空！", "403");
		}
		if (planStartTime == null) {
			throw new BusinessException("计划开始时间不能为空！", "403");
		}
		if (planEndTime == null) {
			throw new BusinessException("计划结束时间不能为空！", "403");
		}
		if(planStartTime > planEndTime){
			throw new BusinessException("计划开始时间不能大于结束时间！", "403");
		}
		return mpDao.addMp(param, user);
	}
	
	@Override
	public Page<MaintenancePlan> queryListPlan(PageRequest pageRequest, MaintenancePlanBo queryBo,User user)
			throws BusinessException, Exception {
		Page<MaintenancePlan> resultPage = mpDao.queryListPlan(pageRequest, queryBo, user);
		return resultPage;
	}
}

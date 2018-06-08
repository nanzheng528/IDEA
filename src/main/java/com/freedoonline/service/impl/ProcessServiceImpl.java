package com.freedoonline.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.domain.ProcessDao;
import com.freedoonline.domain.entity.Process;
import com.freedoonline.service.ProcessService;

import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service("processServiceImpl")
public class ProcessServiceImpl implements ProcessService {

	@Resource(name = "processDaoImpl")
	private ProcessDao processDao;
	
	@Override
	public String addProcess(Process process) {
		if (!StringUtil.hasText(process.getProcessUser())) {
			throw new BusinessException("巡检计划人员为空！", "403");
		}
		if (!StringUtil.hasText(process.getMaintenaceUser())) {
			throw new BusinessException("巡检人员为空！", "403");
		}
		if (!StringUtil.hasText(process.getApprovalUser())) {
			throw new BusinessException("审批人员为空！", "403");
		}
		String processName = StringUtil.hasText(process.getProcessName()) == false ? "巡检计划流程" :process.getProcessName() ;
		process.setProcessName(processName);
		return processDao.addProcess(process);
	}

	@Override
	public List<Map<String, Object>> queryUserListByCloumnName(Map<String,Object> map) {
		if (!StringUtil.hasText((String)map.get("searchUser"))) {
			throw new BusinessException("查询人员列表为空", "403");
		}
		if (!StringUtil.hasText((String)map.get("objectId"))) {
			throw new BusinessException("流程ID为空", "403");
		}
		return processDao.selectUserById(map);
	}
	
	
}

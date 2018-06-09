package com.freedoonline.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
	
	@Resource(name = "commonServiceImpl")
	private CommonServiceImpl commonService;
	
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
		if (!StringUtil.hasText((String)map.get("objectId"))&& null == map.get("maintenaceType")) {
			throw new BusinessException("流程ID和巡检类型不能同时为空", "403");
		}
		return processDao.selectUserById(map);
	}

	@Override
	public List<Map<String, Object>> queryProcess(Process process) {
		return processDao.queryProcess(process);
	}

	@Override
	public String updateProcess(Process process) {
		if (!StringUtil.hasText(process.getProcessUser())) {
			throw new BusinessException("巡检计划人员为空！", "403");
		}
		if (!StringUtil.hasText(process.getMaintenaceUser())) {
			throw new BusinessException("巡检人员为空！", "403");
		}
		if (!StringUtil.hasText(process.getApprovalUser())) {
			throw new BusinessException("审批人员为空！", "403");
		}
		if (!StringUtil.hasText(process.getObjectId())) {
			throw new BusinessException("流程ID为空", "403");
		}
		ArrayList<Object> columnNamesList = new ArrayList<>();
		columnNamesList.add("process_user");
		columnNamesList.add("maintenace_user");
		columnNamesList.add("approval_user");
		columnNamesList.add("modify_user");
		columnNamesList.add("modify_time");
		ArrayList<Object> columnValuesList = new ArrayList<>();
		columnValuesList.add(process.getProcessUser());
		columnValuesList.add(process.getMaintenaceUser());
		columnValuesList.add(process.getApprovalUser());
		columnValuesList.add(process.getModifyUser());
		columnValuesList.add(new Date());
		if(StringUtil.hasText(process.getProcessName())){
			columnNamesList.add("process_name");
			columnValuesList.add(process.getProcessName());
		}
		if(StringUtil.hasText(process.getRemark())){
			columnNamesList.add("remark");
			columnValuesList.add(process.getRemark());
		}
		if( null != process.getActive()){
			columnNamesList.add("active");
			columnValuesList.add(process.getActive());
		}
		
		if ( true ==commonService.updateColumns
				("process", columnNamesList.toArray(new String[]{}), columnValuesList.toArray(), 
						new String[] {"object_id"}, new String[] {process.getObjectId()}, null))
		{
			return process.getObjectId();
		} else {
			 return "";
		}
	}
	
	
}

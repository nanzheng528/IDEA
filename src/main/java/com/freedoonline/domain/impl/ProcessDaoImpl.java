package com.freedoonline.domain.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.common.util.CommonUtil;
import com.freedoonline.common.util.TransformUtil;
import com.freedoonline.domain.ProcessDao;
import com.freedoonline.domain.entity.Process;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class ProcessDaoImpl implements ProcessDao {

	private static final String PROCESS_USER = "process_user"; //巡检计划人员
	private static final String MAINTENACE_USER = "maintenace_user"; //巡检人员
	private static final String APPROVAL_USER = "approval_user"; //审批人员
	
	static String INSERTSQL = "";
	static String SELECT_USER_SQL = ""; 
	static String SELECT_USEROBJECTID_SQL="";
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	static {
		INSERTSQL = "INSERT INTO process(object_id, process_name, process_user, maintenace_user, approval_user, active, create_uesr, create_time, modify_user, modify_time, remark,enp_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
		SELECT_USEROBJECTID_SQL = " from process where object_id = ? ";
		SELECT_USER_SQL = "select  user_name ,object_id from user where object_id in(";
	}
	@Override
	public String addProcess(Process process) {
		String objectId = StringUtil.hasText(process.getObjectId()) == true ? process.getObjectId() : UUID.randomUUID().toString();
		Object[] args = {
			objectId,process.getProcessName(),process.getProcessUser(),process.getMaintenaceUser(),process.getApprovalUser(),
			process.getActive() == null ? 1 : process.getActive() ,process.getCreateUser(), process.getCreateTime() != null ? process.getCreateTime() : new Date(),process.getModifyUser(), process.getModifyTime() != null ? process.getModifyTime() : new Date(),
					process.getRemark(),process.getEnpId()
		};
		
		if(baseJdbcDao.save(INSERTSQL, args) == 1){
			return objectId;
		} else {
			return "";
		}
		
		
	}
	@Override
	public List<Map<String, Object>> selectUserById(Map<String, Object> map) {
		
		StringBuffer stringBuffer = new StringBuffer(SELECT_USEROBJECTID_SQL);
		String objectId = (String) map.get("objectId");
		
		if(PROCESS_USER.equals(map.get("searchUser"))){
			
			return selectUserObejecIdListByCloumn(PROCESS_USER,objectId);
			
			
		} else if (MAINTENACE_USER.equals(map.get("searchUser"))) {
			
			return selectUserObejecIdListByCloumn(MAINTENACE_USER,objectId);
			
		} else if (APPROVAL_USER.equals(map.get("searchUser"))) {
			
			return selectUserObejecIdListByCloumn(APPROVAL_USER,objectId);
		}
		
		else {
			
			throw new BusinessException("输入参数有误", "403");
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> selectUserObejecIdListByCloumn(String searchCloumn,String objectId ){
		Object[] args = {objectId};
		//可以查询出想要查出列表的objectid
		StringBuffer searchBuffer  = new StringBuffer("select " + searchCloumn);
		Map<String, Object> userObejectMap = baseJdbcDao.queryForMap(searchBuffer.append(SELECT_USEROBJECTID_SQL).toString(), args);
		//
		ArrayList<Object> searchArgs = new ArrayList<>();
		
		String userObjectIdList = (String) userObejectMap.get(TransformUtil.humpToLine2(searchCloumn));
		
		String param = CommonUtil.getIdsFormat(userObjectIdList);
		//去掉最后的,加上)
		String lastSbSQL = SELECT_USER_SQL + param + ")";
		return baseJdbcDao.queryForListMap(lastSbSQL, searchArgs.toArray());
	}
}

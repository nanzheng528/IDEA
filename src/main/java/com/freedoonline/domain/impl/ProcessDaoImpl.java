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
	static String SELECT_PROCESS_SQL = "";
	static String UPDATE_PROCESS_SQL = "";
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	static {
		INSERTSQL = "INSERT INTO process(object_id, process_name, process_user, maintenace_user, approval_user, active, create_uesr, create_time, modify_user, modify_time, remark,enp_id ,maintenace_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		SELECT_USEROBJECTID_SQL = " from process where object_id = ? or maintenace_type = ?";
		SELECT_USER_SQL = "select  user_name ,object_id from user where object_id in(";
		SELECT_PROCESS_SQL = "select  object_id,process_name,process_user,maintenace_type,maintenace_user,approval_user,remark from process where  active = '1' ";
	}
	@Override
	public String addProcess(Process process) {
		String objectId = StringUtil.hasText(process.getObjectId()) == true ? process.getObjectId() : UUID.randomUUID().toString();
		Object[] args = {
			objectId,process.getProcessName(),process.getProcessUser(),process.getMaintenaceUser(),process.getApprovalUser(),
			process.getActive() == null ? 1 : process.getActive() ,process.getCreateUser(), process.getCreateTime() != null ? process.getCreateTime() : new Date(),process.getModifyUser(), process.getModifyTime() != null ? process.getModifyTime() : new Date(),
					process.getRemark(),process.getEnpId(),
					null != process.getMaintenaceType() == true ? process.getMaintenaceType() : "1"
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
		Integer maintenaceType = (Integer) map.get("maintenaceType");
		if(PROCESS_USER.equals(map.get("searchUser"))){
			
			return selectUserObejecIdListByCloumn(PROCESS_USER,objectId,maintenaceType);
			
			
		} else if (MAINTENACE_USER.equals(map.get("searchUser"))) {
			
			return selectUserObejecIdListByCloumn(MAINTENACE_USER,objectId,maintenaceType);
			
		} else if (APPROVAL_USER.equals(map.get("searchUser"))) {
			
			return selectUserObejecIdListByCloumn(APPROVAL_USER,objectId,maintenaceType);
		}
		
		else {
			
			throw new BusinessException("输入参数有误", "403");
		}
		
		
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> selectUserObejecIdListByCloumn(String searchCloumn,String objectId,Integer maintenaceType){
		Object[] args = {objectId,maintenaceType};
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
	
	@Override
	public List<Map<String, Object>> queryProcess(Process process) {
		StringBuffer stringBuffer = new StringBuffer(SELECT_PROCESS_SQL);
		ArrayList<Object> args = new ArrayList<>();
		if(StringUtil.hasText(process.getEnpId())){
			stringBuffer.append(" and enp_id = ? ");
			args.add(process.getEnpId());
		}
		if(StringUtil.hasText(process.getObjectId())){
			stringBuffer.append(" and object_id = ? ");
			args.add(process.getObjectId());
		}
		if(null != process.getMaintenaceType()){
			stringBuffer.append(" and maintenace_type = ? ");
			args.add(process.getMaintenaceType());
		}
		if(StringUtil.hasText(process.getProcessName())){
			stringBuffer.append(" and process_name like ? ");
			args.add("%" + process.getProcessName() +"%");
		}
		if(StringUtil.hasText(process.getProcessUser())){
			stringBuffer.append(" and process_user like ? ");
			args.add("%" + process.getProcessUser() +"%");
		}
		if(StringUtil.hasText(process.getMaintenaceUser())){
			stringBuffer.append(" and maintenace_user like ? ");
			args.add("%" + process.getMaintenaceUser() +"%");
		}
		if(StringUtil.hasText(process.getApprovalUser())){
			stringBuffer.append(" and and approval_user like ? ");
			args.add("%" + process.getApprovalUser() +"%");
		}
		return baseJdbcDao.queryForListMap(stringBuffer.toString(), args.toArray());
		
		
	}
}

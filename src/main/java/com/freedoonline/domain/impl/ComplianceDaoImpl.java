package com.freedoonline.domain.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.domain.ComplianceDao;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.bo.BuildingLicenseBo;
import com.freedoonline.service.bo.EnergyBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class ComplianceDaoImpl implements ComplianceDao {
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
	static String SELECT_MAINTENACE_SQL = "";
	static String SELECT_BUILDING_AUTH_SQL = "";
	static String SELECT_EQUIPMENT_AUTH_SQL="";
	static String INSERT_BUILDING_REF = "";
	static String INSERT_USER_REF = "";
	static String INSERT_EQUIPMENT_REF = "";
	
	static {
		SELECT_MAINTENACE_SQL = " SELECT uar.object_id,ua.building_type,ua.auth_type,ua.auth_name,uar.remark,IFNULL(ua.create_time, bu.create_time) as create_time, IFNULL(ua.modify_time, bu.modify_time) as modify_time,IFNULL(uar.auth_status, 0) AS auth_status,	IFNULL(uar.end_time,from_unixtime(665626592)) endTime,IF (NOW() > IFNULL(uar.end_time,from_unixtime(665596800)),0,1) AS overdue , bu.object_id AS building_id,bu.building_name , 1 as sign FROM user_auth ua LEFT JOIN user_auth_ref uar ON ua.auth_type = uar.auth_type LEFT JOIN building bu ON bu.object_id = uar.building_id ";
		SELECT_BUILDING_AUTH_SQL = " SELECT bar.object_id,ba.building_type,ba.auth_type,ba.auth_name,bar.remark,IFNULL(ba.create_time, bu.create_time) as create_time, IFNULL(ba.modify_time, bu.modify_time) as modify_time,IFNULL(bar.auth_status, 0) AS auth_status,IFNULL(bar.end_time,from_unixtime(665626592) ) endTime,if(NOW()>IFNULL(bar.end_time,from_unixtime(665596800) ),0,1) as overdue ,bu.object_id as building_id,bu.building_name ,    2 as sign FROM building_auth ba LEFT JOIN building_auth_ref bar ON ba.auth_type = bar.auth_type LEFT JOIN building bu ON bu.object_id=bar.building_id ";
		SELECT_EQUIPMENT_AUTH_SQL = " SELECT ear.object_id,ea.building_type,ea.auth_type,ea.auth_name,ear.remark,IFNULL(ea.create_time, bu.create_time) as create_time, IFNULL(ea.modify_time, bu.modify_time) as modify_time,IFNULL(ear.auth_status, 0) AS auth_status,IFNULL(ear.end_time,from_unixtime(665626592)) endTime,IF (NOW() > IFNULL(ear.end_time,from_unixtime(665596800)),0,1) AS overdue,ear.building_id AS building_id,bu.building_name ,3 as sign FROM equipment_auth ea LEFT JOIN equipment_auth_ref ear ON ea.auth_type = ear.auth_type  LEFT JOIN building bu ON bu.object_id =ear.building_id ";
		INSERT_BUILDING_REF = " INSERT INTO building_auth_ref (object_id, building_id, auth_type, auth_status, end_time, create_user, create_time, modify_user, modify_time, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		INSERT_USER_REF = " INSERT INTO user_auth_ref (object_id, building_id, auth_type, auth_status, end_time, create_user, create_time, modify_user, modify_time, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		INSERT_EQUIPMENT_REF = " INSERT INTO equipment_auth_ref (object_id, building_id, auth_type, auth_status, end_time, create_user, create_time, modify_user, modify_time, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	}

	@Override
	public Object maintenace(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		StringBuffer buffer = new StringBuffer(SELECT_MAINTENACE_SQL);
		buffer.append(" and uar.building_id=? and  bu.enp_id=? ");
		Object[] args = {buildingId,enpId};
		@SuppressWarnings("unchecked")
		List<Object> resultList = baseJdbcDao.queryForList(buffer.toString(), args, BuildingLicenseBo.class);
		return resultList;
	}
	
	@Override
	public Object buildingLicense(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		StringBuffer buffer = new StringBuffer(SELECT_BUILDING_AUTH_SQL);
		buffer.append(" and bar.building_id=? and  bu.enp_id=? ");
		Object[] args = {buildingId,enpId};
		@SuppressWarnings("unchecked")
		List<Object> resultList = baseJdbcDao.queryForList(buffer.toString(), args, BuildingLicenseBo.class);
		return resultList;
	}
	
	@Override
	public Object equipmentAuth(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		
		StringBuffer buffer = new StringBuffer(SELECT_EQUIPMENT_AUTH_SQL);
		buffer.append(" and ear.building_id= ? and bu.enp_id= ? ");
		Object[] args = {buildingId,enpId};
		@SuppressWarnings("unchecked")
		List<Object> resultList = baseJdbcDao.queryForList(buffer.toString(), args, BuildingLicenseBo.class);
		return resultList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object pushMsg(Map<String, Object> param) {
		
		Comparator<BuildingLicenseBo> comparator = new Comparator<BuildingLicenseBo>() {
			public int compare(BuildingLicenseBo s1, BuildingLicenseBo s2) {
					return s2.getModifyTime().compareTo(s1.getModifyTime());
			}
		};
		//推送人员不合规
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		Object[] args = {buildingId,enpId};
		
		StringBuffer buffer = new StringBuffer(SELECT_MAINTENACE_SQL);
		buffer.append("  and uar.building_id= ? and bu.enp_id= ? where (IF (NOW() > IFNULL(uar.end_time,from_unixtime(665596800)),0,1))='0' or auth_status=0");
		List<BuildingLicenseBo> mResultList = baseJdbcDao.queryForList(buffer.toString(), args, BuildingLicenseBo.class);
		
		//楼宇不合规
		StringBuffer buildingBuffer = new StringBuffer(SELECT_BUILDING_AUTH_SQL);
		buildingBuffer.append(" and bar.building_id=? and  bu.enp_id=? where (IF (NOW() > IFNULL(bar.end_time,from_unixtime(665596800)),0,1))='0' or auth_status=0");
		List<BuildingLicenseBo> bResultList = baseJdbcDao.queryForList(buildingBuffer.toString(), args, BuildingLicenseBo.class);
		
		//设备不合规
		StringBuffer ebuffer = new StringBuffer(SELECT_EQUIPMENT_AUTH_SQL);
		ebuffer.append(" and ear.building_id= ? and bu.enp_id= ? where (IF (NOW() > IFNULL(ear.end_time,from_unixtime(665596800)),0,1))='0' or auth_status=0");
		List<BuildingLicenseBo> eResultList = baseJdbcDao.queryForList(ebuffer.toString(), args, BuildingLicenseBo.class);
		
		mResultList.addAll(bResultList);
		mResultList.addAll(eResultList);
		Collections.sort(mResultList,comparator);
		return mResultList;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Object auth(Map<String, Object> param,String type) {
		//object_id, building_id, auth_type, auth_status, end_time, create_user, create_time, modify_user, modify_time, remark, active
		String objectId = UUID.randomUUID().toString();
		String buildingId = (String)param.get("buildingId");
		String authType = (String)param.get("authType");
		//Date endTime = (Date)param.get("endTime");
		Long endTime1 = (Long)param.get("endTime");
		Date endTime= new Date(endTime1);
		User user = (User)param.get("user");
		String createUser = user.getObjectId();
		String modifyUser = user.getObjectId();
		Object[] args = {objectId,buildingId,authType,1,endTime,createUser,new Date(),modifyUser,new Date(),1};
		if(type.equals("1")){
			baseJdbcDao.save(INSERT_BUILDING_REF, args);
		}else if(type.equals("2")){
			baseJdbcDao.save(INSERT_USER_REF, args);
		}else if(type.equals("3")){
			baseJdbcDao.save(INSERT_EQUIPMENT_REF, args);
		}
		return objectId;
	}
	
	@Override
	public Object authBuilding(String authType, String buildingId) {
		String sql = " SELECT * FROM building_auth_ref bar WHERE bar.auth_type=? AND bar.building_id=? AND bar.active=1 ";
		Object[] args = {authType,buildingId};
		baseJdbcDao.queryForList(sql, args);
		return baseJdbcDao.queryForList(sql, args);
	}
	
	@Override
	public Object authEquipment(String authType, String buildingId) {
		String sql = " SELECT * FROM equipment_auth_ref bar WHERE bar.auth_type=? AND bar.building_id=? AND bar.active=1 ";
		Object[] args = {authType,buildingId};
		baseJdbcDao.queryForList(sql, args);
		return baseJdbcDao.queryForList(sql, args);
	}
	
	@Override
	public Object authUser(String authType, String buildingId) {
		String sql = " SELECT * FROM user_auth_ref bar WHERE bar.auth_type=? AND bar.building_id=? AND bar.active=1 ";
		Object[] args = {authType,buildingId};
		baseJdbcDao.queryForList(sql, args);
		return baseJdbcDao.queryForList(sql, args);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object authScore(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		Object[] args = {buildingId,enpId};
		
		List uList = (List)maintenace(param);
		List bList = (List)buildingLicense(param);
		List eList = (List)equipmentAuth(param);
		
		//人员合规
		StringBuffer buffer = new StringBuffer(SELECT_MAINTENACE_SQL);
		buffer.append("  and uar.building_id= ? and bu.enp_id= ? where (IF (NOW() > IFNULL(uar.end_time,from_unixtime(665596800)),0,1))='1' AND auth_status=1 ");
		List<BuildingLicenseBo> mResultList = baseJdbcDao.queryForList(buffer.toString(), args, BuildingLicenseBo.class);
		//楼宇合规
		StringBuffer buildingBuffer = new StringBuffer(SELECT_BUILDING_AUTH_SQL);
		buildingBuffer.append(" and bar.building_id=? and  bu.enp_id=? where (IF (NOW() > IFNULL(bar.end_time,from_unixtime(665596800)),0,1))='1' AND auth_status=1 ");
		List<BuildingLicenseBo> bResultList = baseJdbcDao.queryForList(buildingBuffer.toString(), args, BuildingLicenseBo.class);
		//设备合规
		StringBuffer ebuffer = new StringBuffer(SELECT_EQUIPMENT_AUTH_SQL);
		ebuffer.append(" and ear.building_id= ? and bu.enp_id= ? where (IF (NOW() > IFNULL(ear.end_time,from_unixtime(665596800)),0,1))='1' AND auth_status=1");
		List<BuildingLicenseBo> eResultList = baseJdbcDao.queryForList(ebuffer.toString(), args, BuildingLicenseBo.class);
		
		Map<String, Object> result = new HashMap<>();
		result.put("building", (bResultList.size()*100)/bList.size());
		result.put("equipment", (eResultList.size()*100)/eList.size());
		result.put("user", (mResultList.size()*100)/uList.size());
		
		result.put("totalScore", (((mResultList.size()*100)/uList.size())+((eResultList.size()*100)/eList.size())+((bResultList.size()*100)/bList.size()))/3);
		
		return result;
	}
}

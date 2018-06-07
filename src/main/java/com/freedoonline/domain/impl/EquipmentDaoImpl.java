package com.freedoonline.domain.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.domain.EquipmentDao;
import com.freedoonline.domain.entity.Equipment;
import com.freedoonline.domain.entity.Hiecs;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.SubSystemFault;
import com.freedoonline.domain.entity.SystemFault;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.bo.EquipmentBo;
import com.freedoonline.service.bo.MaintenancePlanBo;
import com.freedoonline.service.bo.MaintenanceUserBo;
import com.freedoonline.service.bo.SubSystemBo;
import com.freedoonline.service.bo.SystemFaultBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class EquipmentDaoImpl implements EquipmentDao {

	@Autowired
	private BaseJdbcDao baseJdbcDao;

	static String SELECT_EQUIPMENT_SQL = "";
	static String INSERT_EQUIPMENT_SQL = "";
	static String SELECT_MAINTENANCE_PLAN_SQL = "";
	static String INSERT_MAINTENANCE_PLAN_SQL = "";

	static {
		SELECT_EQUIPMENT_SQL = " SELECT b.building_name,es.sub_name , e.object_id, e.sub_num, e.equ_num, e.equ_name, e.equ_level, e.equ_model, e.equ_serial_num, e.quantity, e.purpose, e.years, e.building_id, e.building_floor, e.building_area, e.service_area, e.manufacturer, e.manufacture_time, e.additional_file, e.position, e.parameter, e.circle_type, e.enp_id, e.create_user, e.create_time, e.modify_user, e.modify_time, e.remark, e.active FROM equipment e LEFT JOIN building b ON b.object_id=e.building_id AND b.enp_id=e.enp_id AND b.active=1 AND e.active=1 LEFT JOIN equ_subsystem es ON es.sub_num=e.sub_num AND es.active=1 LEFT JOIN equ_system ess ON es.system_num=ess.system_num AND ess.active=1";
		INSERT_EQUIPMENT_SQL = " INSERT INTO equipment (object_id, sub_num, equ_num, equ_name, equ_level, equ_model, equ_serial_num, quantity, purpose, years, building_id, building_floor, building_area, service_area, manufacturer, manufacture_time, additional_file, position, parameter, circle_type, enp_id, create_user, create_time, modify_user, modify_time, remark, active,building_area_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		SELECT_MAINTENANCE_PLAN_SQL = " SELECT e.equ_name,mpe.user_name AS maintenanceName,mp.object_id,mp.equ_id,mp.maintenance_id,mp.maintenance_content,mp.enp_id,mp.type,mp.frequency,mp.plan_start_time,mp.plan_end_time,mp.STATUS,mp.create_user,mp.create_time,mp.modify_user,mp.modify_time,mp.remark, mp.active , mp.actual_time ,e.building_area,e.building_floor,e.building_id ,bu.building_name ,mp.repair_details, mp.fault ,mp.symptom ,e.service_area ,e.manufacturer ,e.manufacture_time,e.equ_level,e.equ_num,e.equ_model,e.equ_serial_num,ess.sub_num  FROM maintenace_plan mp LEFT JOIN equipment e ON mp.equ_id = e.object_id AND e.active = 1 AND mp.active = 1 LEFT JOIN user mpe ON mp.maintenance_id = mpe.object_id AND mpe.active = 1 LEFT JOIN building bu ON bu.object_id=e.building_id AND bu.active=1 LEFT JOIN equ_subsystem ess ON e.sub_num = ess.sub_num AND ess.active=1 LEFT JOIN equ_system es ON ess.system_num=es.system_num AND es.active=1";
		INSERT_MAINTENANCE_PLAN_SQL = " INSERT INTO maintenace_plan (object_id, equ_id, maintenance_id, maintenance_content, enp_id, plan_start_time, plan_end_time, status, create_user, create_time, modify_user, modify_time, remark, active,name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?) ";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Equipment> queryListEquipment(PageRequest pageRequest, EquipmentBo queryBo) {
		if (!StringUtil.hasText(pageRequest.getOrderBy())) {
			pageRequest.setOrderBy(" e.modify_time DESC ");
		}
		StringBuffer buffer = new StringBuffer(SELECT_EQUIPMENT_SQL);
		buffer.append(" where 1=1 ");
		if (StringUtil.hasText(queryBo.getKeyword())) {
			buffer.append(" and (es.sub_name like '%" + queryBo.getKeyword() + "%'  or e.equ_name like '%"
					+ queryBo.getKeyword() + "%' or e.building_area like '%" + queryBo.getKeyword()
					+ "%' or e.equ_num like '%" + queryBo.getKeyword() + "%' or  e.manufacturer like '%"
					+ queryBo.getKeyword() + "%' or e.equ_model like '%" + queryBo.getKeyword()
					+ "%' or e.equ_serial_num like '%" + queryBo.getKeyword() + "%' or e.equ_level like '%"
					+ queryBo.getKeyword() + "%'  or e.purpose like '%" + queryBo.getKeyword()
					+ "%' or b.building_name like '%" + queryBo.getKeyword() + "%' or e.building_floor like '%"
					+ queryBo.getKeyword() + "%' ) ");
		}
		Map<String, Object> map = getQueryCondition(queryBo);
		buffer.append(map.get("where")); // 拼接条件语句
		Object[] args = (Object[]) map.get("args");
		// buffer.append(" ORDER BY e.modify_time DESC ");
		Page<Equipment> resultPage = baseJdbcDao.queryPage(pageRequest, buffer.toString(), args, Equipment.class);
		
		return resultPage;
	}

	private Map<String, Object> getQueryCondition(EquipmentBo queryBo) {
		// 拼接查询条件
		StringBuffer whereBuffer = new StringBuffer();
		List<Object> args = new ArrayList<Object>();

		if (StringUtil.hasText(queryBo.getEnpId())) {
			// 企业ID
			whereBuffer.append(" and e.enp_id = ? ");
			args.add(queryBo.getEnpId());
		}
		if (StringUtil.hasText(queryBo.getObjectId())) {
			// 设备ID
			whereBuffer.append(" and e.object_id= ? ");
			args.add(queryBo.getObjectId());
		}
		if (StringUtil.hasText(queryBo.getEquName())) {
			// 设备名称
			whereBuffer.append(" and e.equ_name like ? ");
			args.add("%" + queryBo.getEquName() + "%");
		}
		if (StringUtil.hasText(queryBo.getSubName())) {
			// 子系统名称
			whereBuffer.append(" and es.sub_name like ? ");
			args.add("%" + queryBo.getSubName() + "%");
		}
		if (StringUtil.hasText(queryBo.getBuildingId())) {
			// 楼宇ID
			whereBuffer.append(" and e.building_id = ? ");
			args.add(queryBo.getBuildingId());
		}
		if (StringUtil.hasText(queryBo.getBuildingName())) {
			// 楼宇名称
			whereBuffer.append(" and b.building_name like ? ");
			args.add("%" + queryBo.getBuildingName() + "%");
		}
		if (StringUtil.hasText(queryBo.getBuildingFloor())) {
			// 楼层
			whereBuffer.append(" and b.building_floor like ? ");
			args.add("%" + queryBo.getBuildingFloor() + "%");
		}
		if (StringUtil.hasText(queryBo.getBuildingArea())) {
			// 区域
			whereBuffer.append(" and b.building_area like ? ");
			args.add("%" + queryBo.getBuildingArea() + "%");
		}
		if (StringUtil.hasText(queryBo.getEquNum())) {
			// 设备编号
			whereBuffer.append(" and e.equ_num like ? ");
			args.add("%" + queryBo.getEquNum() + "%");
		}
		if (StringUtil.hasText(queryBo.getManufacturer())) {
			// 生产厂家
			whereBuffer.append(" and e.manufacturer like ? ");
			args.add("%" + queryBo.getManufacturer() + "%");
		}
		if (StringUtil.hasText(queryBo.getSubNum())) {
			// 子系统编号
			whereBuffer.append(" AND es.sub_num=? ");
			args.add(queryBo.getSubNum());
		}
		if (StringUtil.hasText(queryBo.getSystemNum())) {
			// 主系统编号
			whereBuffer.append(" AND ess.system_num=? ");
			args.add(queryBo.getSystemNum());
		}
		if (queryBo.getManufactureTime() != null) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String manufactureTime = format.format(queryBo.getManufactureTime());
			whereBuffer.append(" and date_format(e.manufacturer_time,'%Y-%c-%d') like ? ");
			args.add("%" + manufactureTime + "%");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("where", whereBuffer.toString());
		resultMap.put("args", args.toArray());
		return resultMap;
	}

	@Override
	public Object addEquipment(EquipmentBo queryBo) {
		String objectId = StringUtil.hasText(queryBo.getObjectId()) ? queryBo.getObjectId()
				: UUID.randomUUID().toString();
		// object_id, sub_num, equ_num, equ_name, equ_level, equ_model,
		// equ_serial_num,
		// quantity, purpose, years, building_id, building_floor, building_area,
		// service_area,
		// manufacturer, manufacture_time, additional_file, position, parameter,
		// circle_type,
		// enp_id, create_user, create_time, modify_user, modify_time, remark,
		// active
		Object[] args = { objectId, queryBo.getSubNum(), queryBo.getEquNum(), queryBo.getEquName(),
				queryBo.getEquLevel(), queryBo.getEquModel(), queryBo.getEquSerialNum(), queryBo.getQuantity(),
				queryBo.getPurpose(), queryBo.getYears(), queryBo.getBuildingId(), queryBo.getBuildingFloor(),
				queryBo.getBuildingArea(), queryBo.getServiceArea(), queryBo.getManufacturer(),
				queryBo.getManufactureTime(), queryBo.getAdditionalFile(), queryBo.getPosition(),
				queryBo.getParameter(), queryBo.getCircleType(), queryBo.getEnpId(), queryBo.getCreateUser(),
				queryBo.getCreateTime() != null ? queryBo.getCreateTime() : new Date(), queryBo.getModifyUser(),
				queryBo.getModifyTime() != null ? queryBo.getModifyTime() : new Date(), queryBo.getRemark(),
				queryBo.getActive() != null ? queryBo.getActive() : 1 ,queryBo.getBuildingAreaId()};
		baseJdbcDao.save(INSERT_EQUIPMENT_SQL, args);
		return objectId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<MaintenancePlan> queryListMaintenancePlan(PageRequest pageRequest, MaintenancePlanBo queryBo) {
		if (!StringUtil.hasText(pageRequest.getOrderBy())) {
			pageRequest.setOrderBy(" mp.modify_time DESC ");
		}
		StringBuffer buffer = new StringBuffer(SELECT_MAINTENANCE_PLAN_SQL);
		buffer.append(" where 1=1 ");
		Map<String, Object> map = getQueryMaintenancePlan(queryBo);
		buffer.append(map.get("where")); // 拼接条件语句
		Object[] args = (Object[])map.get("args");
		// buffer.append(" ORDER BY e.modify_time DESC ");
		Page<MaintenancePlan> resultPage = baseJdbcDao.queryPage(pageRequest, buffer.toString(), args,
				MaintenancePlan.class);
		return resultPage;
	}

	private Map<String, Object> getQueryMaintenancePlan(MaintenancePlanBo queryBo) {
		// 拼接查询条件
		StringBuffer whereBuffer = new StringBuffer();
		List<Object> args = new ArrayList<Object>();

		if (StringUtil.hasText(queryBo.getEnpId())) {
			// 企业ID
			whereBuffer.append(" AND mp.enp_id= ? ");
			args.add(queryBo.getEnpId());
		}
		if (StringUtil.hasText(queryBo.getObjectId())) {
			// 设备ID
			whereBuffer.append(" and mp.object_id= ? ");
			args.add(queryBo.getObjectId());
		}
		if (StringUtil.hasText(queryBo.getBuildingId())) {
			// 楼宇ID
			whereBuffer.append(" and bu.object_id= ? ");
			args.add(queryBo.getBuildingId());
		}
		if (StringUtil.hasText(queryBo.getSystemNum())) {
			// 主系统编号
			whereBuffer.append(" and es.system_num= ?");
			args.add(queryBo.getSystemNum());
		}
		if(StringUtil.hasText(queryBo.getSubNum())){
			whereBuffer.append(" and ess.sub_num = ? ");
			args.add(queryBo.getSubNum());
		}
		if(null!=queryBo.getFault()){
			whereBuffer.append(" and mp.fault= ? ");
			args.add(queryBo.getFault());
		}
		if(null!=queryBo.getStatus()){
			whereBuffer.append(" and mp.status= ? ");
			args.add(queryBo.getStatus());
		}
		if(!StringUtil.hasText(queryBo.getObjectId())){
			if (null != queryBo.getPlanStartTime() && null != queryBo.getPlanEndTime()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String startTime = format.format(queryBo.getPlanStartTime());
				String endTime = format.format(queryBo.getPlanEndTime());
				whereBuffer.append(" AND mp.plan_start_time between ?  and ?  ");
				args.add(startTime);
				args.add(endTime);
				whereBuffer.append(" AND mp.plan_end_time between ?  and ?  ");
				args.add(startTime);
				args.add(endTime);
			} else if (null != queryBo.getPlanStartTime() && null == queryBo.getPlanEndTime()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String startTime = format.format(queryBo.getPlanStartTime());
				// whereBuffer.append(" AND mp.plan_start_time like ");
				whereBuffer.append(" and date_format(mp.plan_start_time,'%Y-%c-%d') like ");
				args.add("%" + startTime + "%");
			} else if (null == queryBo.getPlanStartTime() && null != queryBo.getPlanEndTime()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String endTime = format.format(queryBo.getPlanEndTime());
				whereBuffer.append(" and date_format(mp.plan_end_time,'%Y-%c-%d') like ");
				args.add("%" + endTime + "%");
			} else {
				whereBuffer.append(
						" AND (mp.plan_start_time between (select date_add(curdate(), interval - day(curdate()) + 1 day))  and (select DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(last_day(curdate()),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND))) and (mp.plan_end_time between (select date_add(curdate(), interval - day(curdate()) + 1 day))  and (select DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(last_day(curdate()),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND)))  ");
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("where", whereBuffer.toString());
		resultMap.put("args", args.toArray());
		return resultMap;
	}
	
	/**
	  * 
	  * <p>功能描述:增加维护计划。</p>	
	  * @param queryBo
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年5月5日 下午7:58:39。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object addMaintenancePlan(MaintenancePlanBo queryBo) {
		String objectId = StringUtil.hasText(queryBo.getObjectId()) ? queryBo.getObjectId()
				: UUID.randomUUID().toString();
		Object[] args = { 
				objectId, queryBo.getEquId(), queryBo.getMaintenanceId(), queryBo.getMaintenanceContent(), queryBo.getEnpId(),
				queryBo.getPlanStartTime(), queryBo.getPlanEndTime(),
				queryBo.getStatus() != null ? queryBo.getStatus() : 0, queryBo.getCreateUser(),
				queryBo.getCreateTime() != null ? queryBo.getCreateTime() : new Date(), queryBo.getModifyUser(),
				queryBo.getModifyTime() != null ? queryBo.getModifyTime() : new Date(), queryBo.getRemark(),
				queryBo.getActive() != null ? queryBo.getActive() : 1,queryBo.getName()
		};
		baseJdbcDao.save(INSERT_MAINTENANCE_PLAN_SQL, args);
		return objectId;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemFault> systemFault(Map<String, Object> param) {
		User user = (User)param.get("user");
		String buildingId = (String) param.get("buildingId");
		StringBuffer buffer = new StringBuffer(" SELECT s.object_id, s.system_num,s.system_name,group_concat(s1.fault SEPARATOR ',') fault FROM equ_system s LEFT JOIN (SELECT es.object_id,es.system_num,es.system_name,mp.fault,e.building_id,e.enp_id FROM maintenace_plan mp LEFT JOIN equipment e ON mp.equ_id = e.object_id AND mp.active = 1 AND e.active = 1 LEFT JOIN equ_subsystem ess ON e.sub_num = ess.sub_num AND ess.active = 1 LEFT JOIN equ_system es ON es.system_num = ess.system_num AND es.active = 1 WHERE e.building_id =? and e.enp_id=?) s1 ON s.system_num = s1.system_num  ");
		//buffer.append(" where 1=1 ");
//		if(StringUtil.hasText(buildingId)){
//			buffer.append(" and e.building_id = ? ");
//		}
//		if(StringUtil.hasText(user.getEnpId())){
//			buffer.append(" and e.enp_id = ? ");
//		}
		buffer.append("  GROUP BY s.system_num ");
		Object[] args ={buildingId,user.getEnpId()};
		return baseJdbcDao.queryForList(buffer.toString(), args, SystemFault.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubSystemBo> querySub(String systemId, String enpId) {
		StringBuffer buffer = new StringBuffer(" SELECT  ess.sub_num ,ess.sub_name FROM equ_subsystem ess LEFT JOIN equ_system es ON ess.system_num=es.system_num where  es.system_num=? and es.enp_id=?  and  es.active=1");
		Object[] args ={systemId,enpId};
		return  baseJdbcDao.queryForList(buffer.toString(), args, SubSystemBo.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubSystemFault> subSystemFault(Map<String, Object> param) {
		User user = (User)param.get("user");
		String buildingId = (String) param.get("buildingId");
		String systemNum = (String) param.get("systemNum");
		StringBuffer buffer = new StringBuffer(" SELECT ess.sub_name AS system_name,ess.sub_num AS system_num,temp1.fault,temp1.equ_level FROM equ_subsystem ess LEFT JOIN (SELECT ess.sub_num AS system_num,ess.sub_name AS system_name,group_concat(mp.fault SEPARATOR ',') fault,e.building_id, group_concat(e.equ_level SEPARATOR ',') equ_level  FROM equ_subsystem ess LEFT JOIN equ_system es ON ess.system_num = es.system_num AND ess.active = 1 AND es.active = 1 LEFT JOIN equipment e ON e.sub_num = ess.sub_num AND e.active = 1 LEFT JOIN maintenace_plan mp ON mp.equ_id = e.object_id AND mp.active = 1 WHERE e.building_id = ? AND e.enp_id = ? GROUP BY ess.sub_num) temp1 ON ess.sub_num = temp1.system_num WHERE ess.system_num = ? ");
		Object[] args ={buildingId,user.getEnpId(),systemNum};
		return  baseJdbcDao.queryForList(buffer.toString(), args, SubSystemFault.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MaintenancePlan> equSystemPct(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String systemNum = (String)param.get("systemNum");
		StringBuffer buffer = new StringBuffer(SELECT_MAINTENANCE_PLAN_SQL);
		buffer.append(" where bu.object_id= ? and es.system_num=? AND mp.fault=0  ");
		Object[] args ={buildingId,systemNum};
		return  baseJdbcDao.queryForList(buffer.toString(), args, MaintenancePlan.class);
	}
	
	@Override
	public Object pushMsg(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		
		StringBuffer buffer = new  StringBuffer(" SELECT e.equ_name,mpe. NAME AS maintenanceName,mp.object_id,mp.equ_id,mp.maintenance_id,mp.maintenance_content,mp.enp_id,mp.type,mp.frequency,mp.plan_start_time,mp.plan_end_time,mp.STATUS,mp.create_user,mp.create_time,mp.modify_user,mp.modify_time,mp.remark, mp.active , mp.actual_time ,e.building_area,e.building_floor,e.building_id ,bu.building_name ,mp.repair_details, mp.fault ,mp.symptom ,e.service_area ,e.manufacturer ,e.manufacture_time,e.equ_level,e.equ_num,e.equ_model,e.equ_serial_num,ess.sub_num  ,4 as sign FROM maintenace_plan mp LEFT JOIN equipment e ON mp.equ_id = e.object_id AND e.active = 1 AND mp.active = 1 LEFT JOIN maintenance_person mpe ON mp.maintenance_id = mpe.object_id AND mpe.active = 1 LEFT JOIN building bu ON bu.object_id=e.building_id AND bu.active=1 LEFT JOIN equ_subsystem ess ON e.sub_num = ess.sub_num AND ess.active=1 LEFT JOIN equ_system es ON ess.system_num=es.system_num AND es.active=1 ");
		buffer.append(" where mp.enp_id=?  AND  bu.object_id=? AND mp.fault='0' ORDER BY mp.fault asc  ");
		Object[] args ={enpId,buildingId};
		return baseJdbcDao.queryForList(buffer.toString(), args, MaintenancePlan.class);
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public Object faultRate(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		// 获取总设备
		String totalSql = " SELECT SUM(e.quantity) quantity  FROM  equipment e WHERE e.building_id=? AND e.enp_id=? GROUP BY enp_id ";
		String faultSql = " SELECT COUNT(mp.fault) fault from  maintenace_plan mp LEFT JOIN equipment e ON mp.equ_id=e.object_id AND mp.active=1 AND e.active=1 WHERE e.building_id=? AND e.enp_id=? AND mp.fault='0' ";
		Object[] args ={buildingId,enpId};
		List <Equipment> elist = baseJdbcDao.queryForList(totalSql, args, Equipment.class);
		List <MaintenancePlan> mlist =baseJdbcDao.queryForList(faultSql, args, MaintenancePlan.class);
		Map<String, Object> resultMap = new HashMap<>();
		if(elist==null && elist.size()<=0){
			return resultMap.put("fault", 0);
		}
		if(mlist==null && mlist.size()<=0){
			return resultMap.put("fault", 0);
		}
		resultMap.put("fault", ((mlist.get(0).getFault()*100)/elist.get(0).getQuantity()));
		return resultMap;
	}
	
	@Override
	public Equipment queryEquById(String objectId,String enpId) {
		StringBuffer buffer = new StringBuffer(SELECT_EQUIPMENT_SQL);
		buffer.append(" where e.object_id=? AND e.active=1  AND e.enp_id=? ");
		Object[] args = {objectId,enpId};
		return (Equipment)baseJdbcDao.queryForObject(buffer.toString(), args, Equipment.class);
	}
	
	@Override
	public Object totalSystemEqu(String buildingId) {
		StringBuffer buffer = new StringBuffer(" SELECT es.system_num,es.system_name,count(e.object_id) equNum,group_concat(mp.fault SEPARATOR ',') fault FROM equ_system es LEFT JOIN equ_subsystem ess ON es.system_num = ess.system_num AND es.active=1 AND ess.active=1 LEFT JOIN equipment e ON e.sub_num=ess.sub_num  AND e.active=1 LEFT JOIN maintenace_plan mp ON mp.equ_id = e.object_id AND mp.active=1  WHERE e.building_id=? GROUP BY es.system_num ");
		Object[] args = {buildingId};
		return baseJdbcDao.queryForList(buffer.toString(), args, SystemFaultBo.class);
	}
	
	@Override
	public Object queryMaintenanceUser(Map<String, Object> paramMap) {
		String enpId = (String)paramMap.get("enpId");
		String sql = " select object_id , name from maintenance_person mp where enp_id=? ";
		Object[] args = {enpId};
		return baseJdbcDao.queryForList(sql, args, MaintenanceUserBo.class);
	}
}

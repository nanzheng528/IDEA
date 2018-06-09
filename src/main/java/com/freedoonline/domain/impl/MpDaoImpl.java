package com.freedoonline.domain.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.domain.MpDao;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.User;
import com.freedoonline.service.bo.MaintenancePlanBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class MpDaoImpl implements MpDao {
	
	static String INSERT_MP_SQL = "";
	static String SELECT_MP_SQL = "";
	
	static {
		INSERT_MP_SQL = " INSERT INTO maintenace_plan (object_id, name, equ_id, maintenance_id, enp_id, type, frequency, plan_start_time,actual_time, status, create_user, create_time, modify_user, modify_time, remark, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		SELECT_MP_SQL = " SELECT e.equ_name,mp.name,mpe.user_name AS maintenanceName,mp.object_id,mp.equ_id,mp.maintenance_id,mp.maintenance_content,mp.enp_id,mp.type,mp.frequency,mp.plan_start_time,mp.plan_end_time,mp.STATUS,mp.create_user,mp.create_time,mp.modify_user,mp.modify_time,mp.remark, mp.active , mp.actual_time ,e.building_area,e.building_floor,e.building_id ,bu.building_name ,mp.repair_details, mp.fault ,mp.symptom ,e.service_area ,e.manufacturer ,e.manufacture_time,e.equ_level,e.equ_num,e.equ_model,e.equ_serial_num,ess.sub_num  FROM maintenace_plan mp LEFT JOIN equipment e ON mp.equ_id = e.object_id AND e.active = 1 LEFT JOIN user mpe ON mp.maintenance_id = mpe.object_id AND mpe.active = 1 LEFT JOIN building bu ON bu.object_id=e.building_id AND bu.active=1 LEFT JOIN equ_subsystem ess ON e.sub_num = ess.sub_num AND ess.active=1 LEFT JOIN equ_system es ON ess.system_num=es.system_num AND es.active=1 ";
	}
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object addMp(Map<String, Object> param, User user) {
		List <String> listObjectId = new ArrayList<>();
		//
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar=Calendar.getInstance();
		
		Long planStartTime = (Long) param.get("planStartTime");
		Long planEndTime = (Long) param.get("planEndTime");
		Long time=calendar.getTimeInMillis();
		
		if(planStartTime+86400000<time){
			planStartTime=time;
		}
		
		List <String> list = (List<String>)param.get("equId");
		int freq= (Integer)param.get("frequency");
		
		for(String equId:list){
			if(freq==1){
				int dayNum = Integer.parseInt(String.valueOf(((planEndTime-planStartTime)/(1000*3600*24))));
				for(int i=0;i<=dayNum;i++){
					calendar.setTimeInMillis(planStartTime);
					calendar.add(Calendar.DATE,i);
					String objectId = UUID.randomUUID().toString();
					Object[] args =  {
							objectId,param.get("name"),equId,param.get("maintenanceId"),user.getEnpId(),param.get("type"),
							param.get("frequency"),calendar.getTime(),param.get("actualTime"),param.get("status")!=null?param.get("status")!=null:0,user.getObjectId(),
							new Date(),user.getObjectId(),new Date(),param.get("remark"),1
					};
					listObjectId.add(objectId);
					baseJdbcDao.save(INSERT_MP_SQL, args);
				}
				calendar.add(Calendar.DATE,-dayNum);
				
			}else if(freq==2){
				int dayNum = 0;
				calendar.setTimeInMillis(planStartTime);
				if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ){
					//calendar.setTimeInMillis(planStartTime);
					calendar.add(Calendar.DATE,2);
					planStartTime = calendar.getTimeInMillis();
					dayNum+=2;
				}else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
					//calendar.setTimeInMillis(planStartTime);
					calendar.add(Calendar.DATE,1);
					dayNum+=1;
					planStartTime = calendar.getTimeInMillis();
				}else{
					//calendar.setTimeInMillis(planStartTime);
					planStartTime = calendar.getTimeInMillis();
				}
				
				calendar.setTimeInMillis(planEndTime);
				if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY ){
					calendar.add(Calendar.DATE,-1);
					planStartTime = calendar.getTimeInMillis();
					dayNum+=2;
				}else if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
					calendar.add(Calendar.DATE,-2);
					dayNum+=1;
					planStartTime = calendar.getTimeInMillis();
				}else{
					planEndTime = calendar.getTimeInMillis();
				}
				
				//------------------------------//
				int weekNum = (int)countTwoDayWeek(planStartTime,planEndTime);
				calendar.setTimeInMillis(planStartTime);
				
				for(int i=1;i<weekNum-1;i++){
					calendar.add(Calendar.DATE,i*7);
					dayNum+=(i*7);
					String objectId = UUID.randomUUID().toString();
					Object[] args =  {
							objectId,param.get("name"),equId,param.get("maintenanceId"),user.getEnpId(),param.get("type"),
							param.get("frequency"),calendar.getTime(),param.get("actualTime"),param.get("status")!=null?param.get("status")!=null:0,user.getObjectId(),
							new Date(),user.getObjectId(),new Date(),param.get("remark"),1
					};
					listObjectId.add(objectId);
					baseJdbcDao.save(INSERT_MP_SQL, args);
				}
				Long [] time2 = {planStartTime,planEndTime};
				for(Long l1 :time2){
					String objectId = UUID.randomUUID().toString();
					Object[] args =  {
							objectId,param.get("name"),equId,param.get("maintenanceId"),user.getEnpId(),param.get("type"),
							param.get("frequency"),new Date(l1),param.get("actualTime"),param.get("status")!=null?param.get("status")!=null:0,user.getObjectId(),
							new Date(),user.getObjectId(),new Date(),param.get("remark"),1
					};
					listObjectId.add(objectId);
					baseJdbcDao.save(INSERT_MP_SQL, args);
				}
				calendar.add(Calendar.DATE,-dayNum);
			}else if(freq==3){
//				DateTime start = new DateTime(startDate.getTime());
//				DateTime end= new DateTime(endDate.getTime());
//				int months = Months.monthBetween(start, end).getMonths();
			}
		}
		return listObjectId;
	}
	
	public Object countTwoDayWeek(Long startDate, Long endDate) {
		long CONST_WEEK = 3600 * 1000 * 24 * 7;
		Calendar before = Calendar.getInstance();
		Calendar after = Calendar.getInstance();
		before.setTimeInMillis(startDate);
		after.setTimeInMillis(endDate);
		int week = before.get(Calendar.DAY_OF_WEEK);
		before.add(Calendar.DATE, -week);
		week = after.get(Calendar.DAY_OF_WEEK);
		after.add(Calendar.DATE, 7 - week);
		int interval = (int) ((after.getTimeInMillis() - before.getTimeInMillis()) / CONST_WEEK);
		// interval = interval - 1;
		return interval;
	}
	
	@Override
	public Page<MaintenancePlan> queryListPlan(PageRequest pageRequest, MaintenancePlanBo queryBo,User user) {
		Integer roleId = user.getRoleId();
		if(roleId!=1){
			queryBo.setMaintenanceId(user.getObjectId());
		}
		StringBuffer buffer = new StringBuffer(SELECT_MP_SQL);
		buffer.append(" where 1=1 and mp.active = 1 ");
		Map<String, Object> map = getQueryMPlan(queryBo);
		buffer.append(map.get("where")); // 拼接条件语句
		Object[] args = (Object[])map.get("args");
		Page<MaintenancePlan> resultPage = baseJdbcDao.queryPage(pageRequest, buffer.toString(), args,MaintenancePlan.class);
		return resultPage;
	}
	
	private Map<String, Object> getQueryMPlan(MaintenancePlanBo queryBo) {
		// 拼接查询条件
		StringBuffer whereBuffer = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		if (StringUtil.hasText(queryBo.getEnpId())) {
			// 企业ID
			whereBuffer.append(" AND mp.enp_id= ? ");
			args.add(queryBo.getEnpId());
		}
		
		if (StringUtil.hasText(queryBo.getObjectId())) {
			// 明细
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
		if(StringUtil.hasText(queryBo.getMaintenanceId())){
			whereBuffer.append(" and mp.maintenance_id= ? ");
			args.add(queryBo.getMaintenanceId());
		}
		if (null != queryBo.getPlanStartTime() && null != queryBo.getPlanEndTime()) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String startTime = format.format(queryBo.getPlanStartTime());
			String endTime = format.format(queryBo.getPlanEndTime());
			whereBuffer.append(" AND mp.plan_start_time between ?  and ?  ");
			args.add(startTime);
			args.add(endTime);
//			whereBuffer.append(" AND mp.plan_end_time between ?  and ?  ");
//			args.add(startTime);
//			args.add(endTime);
		}
//		if(!StringUtil.hasText(queryBo.getObjectId())){
//			if (null != queryBo.getPlanStartTime() && null != queryBo.getPlanEndTime()) {
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//				String startTime = format.format(queryBo.getPlanStartTime());
//				String endTime = format.format(queryBo.getPlanEndTime());
//				whereBuffer.append(" AND mp.plan_start_time between ?  and ?  ");
//				args.add(startTime);
//				args.add(endTime);
//				whereBuffer.append(" AND mp.plan_end_time between ?  and ?  ");
//				args.add(startTime);
//				args.add(endTime);
//			} else if (null != queryBo.getPlanStartTime() && null == queryBo.getPlanEndTime()) {
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//				String startTime = format.format(queryBo.getPlanStartTime());
//				// whereBuffer.append(" AND mp.plan_start_time like ");
//				whereBuffer.append(" and date_format(mp.plan_start_time,'%Y-%c-%d') like ");
//				args.add("%" + startTime + "%");
//			} else if (null == queryBo.getPlanStartTime() && null != queryBo.getPlanEndTime()) {
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//				String endTime = format.format(queryBo.getPlanEndTime());
//				whereBuffer.append(" and date_format(mp.plan_end_time,'%Y-%c-%d') like ");
//				args.add("%" + endTime + "%");
//			} else {
//				whereBuffer.append(
//						" AND (mp.plan_start_time between (select date_add(curdate(), interval - day(curdate()) + 1 day))  and (select DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(last_day(curdate()),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND))) and (mp.plan_end_time between (select date_add(curdate(), interval - day(curdate()) + 1 day))  and (select DATE_ADD(DATE_ADD(str_to_date(DATE_FORMAT(last_day(curdate()),'%Y-%m-%d'),'%Y-%m-%d %H:%i:%s'),INTERVAL 1 DAY),INTERVAL -1 SECOND)))  ");
//			}
//		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("where", whereBuffer.toString());
		resultMap.put("args", args.toArray());
		return resultMap;
	}
	
}

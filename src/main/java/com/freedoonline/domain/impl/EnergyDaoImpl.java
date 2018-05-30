package com.freedoonline.domain.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.domain.EnergyDao;
import com.freedoonline.domain.entity.Building;
import com.freedoonline.domain.entity.Electricity;
import com.freedoonline.domain.entity.Hiecs;
import com.freedoonline.service.bo.EnergyBo;
import com.freedoonline.service.bo.EnergyTypeBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class EnergyDaoImpl implements EnergyDao {
	
	static String SELECT_ENERGY_SQL = "";
	static String INSERT_ELECTRICITY_SQL = "";
	
	static {
		SELECT_ENERGY_SQL = "";
		INSERT_ELECTRICITY_SQL = " INSERT INTO electricity (object_id, multimeter_id, bill_time, total_power, declare_md, actual_md, cost, create_user, create_time, modify_user, modify_time, remark, active, enp_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	}

	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object waterConsumption(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String timeSlot = (String)param.get("timeSlot");
		Integer eneType = (Integer)param.get("eneType");
		String enpId = (String)param.get("enpId");
		String startTime = (String)param.get("startTime");
		String endTime = (String)param.get("endTime");
		String sql = "";
		Object[] args = {startTime,endTime,buildingId,enpId,"%"+eneType+"%"};
		switch (timeSlot) {
		case "1":
			sql = " SELECT e.modify_time AS date,e.consume_num AS value,1 AS sign FROM energy e LEFT JOIN multimeter m ON e.active=1 AND m.active=1 AND e.multimeter_id=m.object_id  WHERE (e.modify_time BETWEEN ? AND ? ) AND m.building_id=? AND m.enp_id=? AND e.ene_type like ? ";
			break;
		case "2":
			sql = " select CONCAT(YEAR(e.modify_time),'-',DATE_FORMAT(e.modify_time,'%m')) date ,SUM(e.consume_num)  as value,2 AS sign   from energy e LEFT JOIN multimeter m ON e.active=1 AND m.active=1 AND e.multimeter_id=m.object_id  WHERE (e.modify_time BETWEEN ? AND ? ) AND m.building_id=? AND m.enp_id=? AND e.ene_type like ? group by date ";
			break;
		case "3":
			sql = " select CONCAT(YEAR(e.modify_time),'_',quarter(e.modify_time)) date , SUM(e.consume_num) as value,3 AS sign  from energy e LEFT JOIN multimeter m ON e.active=1 AND m.active=1 AND e.multimeter_id=m.object_id  WHERE (e.modify_time BETWEEN ? AND ? ) AND m.building_id=? AND m.enp_id=? AND e.ene_type like ? group by date ";
			break;
		case "4":
			sql = " SELECT DATE_FORMAT(e.modify_time,'%Y') as date , SUM(e.consume_num) AS value,4 AS sign  FROM energy e LEFT JOIN multimeter m ON e.active=1 AND m.active=1 AND e.multimeter_id=m.object_id  WHERE (e.modify_time BETWEEN ? AND ? ) AND m.building_id=? AND m.enp_id=? AND e.ene_type like ? group by date  ";
			break;
		default:
			sql = " SELECT e.modify_time AS date,e.consume_num AS value,1 AS sign FROM energy e LEFT JOIN multimeter m ON e.active=1 AND m.active=1 AND e.multimeter_id=m.object_id  WHERE (e.modify_time BETWEEN ? AND ? ) AND m.building_id=? AND m.enp_id=? AND e.ene_type like ? ";
			break;
		}
		List<Object> resultList = baseJdbcDao.queryForList(sql, args, EnergyBo.class);
		return resultList;
	}
	
	@Override
	public Object eleAdd(Electricity electricity) {
		String objectId = StringUtil.hasText(electricity.getObjectId())?electricity.getObjectId():UUID.randomUUID().toString();
		//object_id, multimeter_id, bill_time, total_power, declare_md, actual_md, 
		//cost, create_user, create_time, modify_user, modify_time, remark, active, enp_id
		Object[] args =  {
			objectId, electricity.getMultimeterId(),electricity.getBillTime(),electricity.getTotalPower(),
			electricity.getDeclareMd(),electricity.getActualMd(),electricity.getCost(),electricity.getCreateUser(),
			electricity.getCreateTime()!=null?electricity.getCreateTime():new Date(),electricity.getModifyUser(),
			electricity.getModifyTime()!=null?electricity.getModifyTime():new Date(),electricity.getRemark(),1,electricity.getEnpId()
		};
		baseJdbcDao.save(INSERT_ELECTRICITY_SQL,  args);
		return objectId;
	}
	
	@Override
	public Electricity queryEleById(String objectId, String enpId) {
		StringBuffer buffer = new StringBuffer(" SELECT e.object_id, e.multimeter_id, e.bill_time, e.total_power, e.declare_md, e.actual_md, e.cost, e.modify_user, e.modify_time, e.remark, e.enp_id from electricity e ");
		buffer.append(" where e.object_id = ? AND e.active = 1 and e.enp_id=?");
		Object[] args = {objectId,enpId};
		Electricity ele =  (Electricity)baseJdbcDao.queryForObject(buffer.toString(), args, Electricity.class);
		return ele;
	}
	
	@Override
	public Object eleQuery(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String timeSlot = (String)param.get("timeSlot");
		Integer eneType = (Integer)param.get("eneType");
		String enpId = (String)param.get("enpId");
//		String startTime = (String)param.get("startTime");
//		String endTime = (String)param.get("endTime");
		Object[] args = {buildingId,enpId,eneType+"%"};
		
		String sql = "";
		
		if(timeSlot.equals("1")){
			sql = " SELECT e.ene_type,SUM(e.consume_num) as consume_num  from energy e LEFT JOIN equipment equ ON e.equ_id=equ.object_id  AND e.active=1 AND equ.active=1  WHERE equ.building_id=?  AND  equ.enp_id= ?  AND  (date(e.modify_time) = curdate())     AND  e.ene_type like ? GROUP BY e.ene_type ";
			return baseJdbcDao.queryForList(sql, args, EnergyTypeBo.class);
		}else if(timeSlot.equals("2")){
			sql = " select CONCAT(YEAR(e.modify_time),'-',DATE_FORMAT(e.modify_time,'%m')) date,SUM(e.consume_num) AS consume_num,e.ene_type FROM energy e LEFT JOIN  equipment equ ON e.equ_id=equ.object_id AND e.active=1 AND equ.active=1 WHERE (e.modify_time BETWEEN (SELECT DATE_SUB(CURDATE(),INTERVAL dayofyear(now())-1 DAY)) AND (SELECT concat(YEAR(now()),'-12-31 23:59:59'))) AND equ.building_id =? AND equ.enp_id =? AND e.ene_type LIKE ? GROUP BY date,e.ene_type ORDER BY date asc ";
			return baseJdbcDao.queryForList(sql, args, EnergyTypeBo.class);
		}else if(timeSlot.equals("3")){
			sql = " SELECT CONCAT(YEAR (e.modify_time),'_',QUARTER (e.modify_time)) date,SUM(e.consume_num) AS consume_num,e.ene_type FROM energy e LEFT JOIN  equipment equ ON e.equ_id=equ.object_id AND e.active=1 AND equ.active=1 WHERE (e.modify_time BETWEEN (SELECT DATE_SUB(CURDATE(),INTERVAL dayofyear(now())-1 DAY)) AND (SELECT concat(YEAR(now()),'-12-31 23:59:59'))) AND equ.building_id =? AND equ.enp_id =? AND e.ene_type LIKE ? GROUP BY date,e.ene_type ORDER BY date asc ";
			return baseJdbcDao.queryForList(sql, args, EnergyTypeBo.class);
		}else if(timeSlot.equals("4")){
			sql = " SELECT DATE_FORMAT(e.modify_time,'%Y') as date,SUM(e.consume_num) AS consume_num,e.ene_type FROM energy e LEFT JOIN  equipment equ ON e.equ_id=equ.object_id AND e.active=1 AND equ.active=1 WHERE (e.modify_time BETWEEN (SELECT DATE_SUB(CURDATE(),INTERVAL dayofyear(now())-1 DAY)) AND (SELECT concat(YEAR(now()),'-12-31 23:59:59'))) AND equ.building_id =? AND equ.enp_id =? AND e.ene_type LIKE ? GROUP BY date,e.ene_type ORDER BY date asc ";
			return baseJdbcDao.queryForList(sql, args, EnergyTypeBo.class);
		}
		return null;
	}
}

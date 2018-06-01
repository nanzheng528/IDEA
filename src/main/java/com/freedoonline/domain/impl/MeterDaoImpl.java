package com.freedoonline.domain.impl;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Repository;

import com.freedoonline.controller.MeterController;
import com.freedoonline.domain.MeterDao;
import com.freedoonline.domain.entity.Meter;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class MeterDaoImpl implements MeterDao {

	private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
	@Autowired
	private BaseJdbcDao baseJdbcDao;
    
	static String INSERTMETERSQL;
	static String DELTMETERSQL;
	static String UPDATESQL;
	
	static{
		INSERTMETERSQL = "INSERT INTO meter (object_id, building_id, building_area_id, name, number, type, energy_type,unit, "
				+ "service_area,ul_alarm, ll_alarm,status, create_user, create_time, modify_user, modify_time, active, remark)VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		DELTMETERSQL = "update meter SET active = ?,modify_user = ?,modify_time = ? where object_id = ?";
		UPDATESQL = "update meter SET active = ?,modify_user = ?,modify_time = ? where object_id = ?";
	}
	
	public String addMeter(Meter meter) {
		String objectId = (!StringUtil.hasText(meter.getObjectId())) ? UUID.randomUUID().toString() : meter.getObjectId();
		Object [] args = {objectId,meter.getBuildingId(),meter.getBuildingAreaId(),meter.getName(),meter.getNumber()
				,meter.getType(),meter.getEnergyType(),meter.getUnit(),meter.getServiceArea(),meter.getUlAlarm(),meter.getLlAlarm()
				,meter.getStatus() != null ? meter.getStatus():1
				,meter.getCreateUser(),meter.getCreateTime() != null ? meter.getCreateTime():new Date()
				,meter.getModifyUser(),meter.getModifyTime() != null ? meter.getModifyTime():new Date()
					,meter.getActive() != null ? meter.getActive() : 1,meter.getRemark() 	};
		baseJdbcDao.save(INSERTMETERSQL, args);
		logger.info("-----------meterdao插入数据成功--------------");
		return objectId;
		
	}

	@Override
	public int delMeter(Map<String, Object> map) {
		Object [] args = {
				0,map.get("modifyUser"),new Date(),map.get("objectId"),
		};
	return	baseJdbcDao.update(DELTMETERSQL, args);

	}

	@Override
	public int updateMeter(Meter meter) {
		
		return 0;
	}

}

package com.freedoonline.domain.impl;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	static{
		INSERTMETERSQL = "INSERT INTO meter (object_id, building_id, building_area_id, name, number, type, energy_type, "
				+ "service_area,ul_alarm, ll_alarm, create_user, create_time, modify_user, modify_time, active, remark)VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}
	
	public String addMeter(Meter meter) {
		String objectId = (!StringUtil.hasText(meter.getObjectId())) ? UUID.randomUUID().toString() : meter.getObjectId();
		Object [] args = {objectId,meter.getBuildingId(),meter.getBuildingAreaId(),meter.getName(),meter.getNumber()
				,meter.getType(),meter.getEnergyType(),meter.getServiceArea(),meter.getUlAlarm(),meter.getLlAlarm()
				,meter.getCreateUser(),meter.getCreateTime() != null ? meter.getCreateTime():new Date()
				,meter.getModifyUser(),meter.getModifyTime() != null ? meter.getModifyTime():new Date()
					,meter.getActive() != null ? meter.getActive() : 1,meter.getRemark() 	};
		baseJdbcDao.save(INSERTMETERSQL, args);
		logger.info("-----------meterdao插入数据成功--------------");
		return objectId;
		
	}

}

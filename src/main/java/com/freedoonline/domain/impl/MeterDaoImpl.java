package com.freedoonline.domain.impl;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.UpdatableSqlQuery;
import org.springframework.stereotype.Repository;

import com.freedoonline.controller.MeterController;
import com.freedoonline.domain.MeterDao;
import com.freedoonline.domain.entity.Meter;
import com.freedoonline.service.bo.MeterBo;

import antlr.collections.List;
import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class MeterDaoImpl implements MeterDao {

	private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
	@Autowired
	private BaseJdbcDao baseJdbcDao;

	private static final int RUNSTATUS = 1;
	private static final int UNRUNSTATUS = 0;
	private static final int ACTIVE = 1;
	private static final int UNACTIVE = 0;

	static String INSERTMETERSQL;
	static String DELTMETERSQL;
	static String UPDATESQL;
	static String SELECTSQL;

	static {
		INSERTMETERSQL = "INSERT INTO meter (object_id, building_id, building_area_id, name, number, type, energy_type,unit, "
				+ "service_area,ul_alarm, ll_alarm,status, create_user, create_time, modify_user, modify_time, active, remark)VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		DELTMETERSQL = "update meter SET active = ?,modify_user = ?,modify_time = ? where object_id = ?";
		UPDATESQL = "update meter SET name  = ?,number = ?,type = ?,energy_type = ?,unit = ?,building_area_id = ?,ul_alarm =?,ll_alarm = ?,status = ?,"
				+ "modify_user = ?,modify_time = ?" + "where object_id = ? and building_id = ?";
		SELECTSQL = "select m.number, m.name, m.type, m.building_area_id, m.status ,m.object_id, b.enp_id" +
				" from meter m left join  building b on m.building_id = b.object_id where b.enp_id = ? and m.active = '1'";
	}

	public String addMeter(Meter meter) {
		String objectId = (!StringUtil.hasText(meter.getObjectId())) ? UUID.randomUUID().toString()
				: meter.getObjectId();
		Object[] args = { objectId, meter.getBuildingId(), meter.getBuildingAreaId(), meter.getName(),
				meter.getNumber(), meter.getType(), meter.getEnergyType(), meter.getUnit(), meter.getServiceArea(),
				meter.getUlAlarm(), meter.getLlAlarm(), meter.getStatus() != null ? meter.getStatus() : 1,
				meter.getCreateUser(), meter.getCreateTime() != null ? meter.getCreateTime() : new Date(),
				meter.getModifyUser(), meter.getModifyTime() != null ? meter.getModifyTime() : new Date(),
				meter.getActive() != null ? meter.getActive() : ACTIVE, meter.getRemark() };
		baseJdbcDao.save(INSERTMETERSQL, args);
		logger.info("-----------meterdao插入数据成功--------------");
		return objectId;

	}

	@Override
	public int delMeter(Map<String, Object> map) {
		Object[] args = { UNACTIVE, map.get("modifyUser"), new Date(), map.get("objectId"), };
		return baseJdbcDao.update(DELTMETERSQL, args);

	}

	@Override
	public int updateMeter(Meter meter) {
		Object[] args = { meter.getName(), meter.getNumber(), meter.getType(), meter.getEnergyType(), meter.getUnit(),
				meter.getBuildingAreaId(), meter.getUlAlarm(), meter.getLlAlarm(),
				meter.getStatus() != null ? meter.getStatus() : RUNSTATUS, meter.getModifyUser(),
				meter.getModifyTime() != null ? meter.getModifyTime() : new Date(), meter.getObjectId(),
				meter.getBuildingId() };
		return baseJdbcDao.update(UPDATESQL, args);

	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Meter> queryMeterData(PageRequest pageRequest, MeterBo meter) {
		if (!StringUtil.hasText(pageRequest.getOrderBy())) {
			pageRequest.setOrderBy(" m.modify_time DESC ");
		}
		ArrayList<Object> args = new ArrayList<>();
		args.add(meter.getEnpId());
		StringBuffer selectSqlBuffer = new StringBuffer(SELECTSQL);
		if (StringUtil.hasText(meter.getNumber())){
			selectSqlBuffer.append(" and m.number like ?");
			args.add("%"+meter.getNumber()+"%");
		}
		if (StringUtil.hasText(meter.getName())){
			selectSqlBuffer.append(" and m.name like ?");
			args.add("%"+meter.getName()+"%");
		}
		if (meter.getType() != null){
			selectSqlBuffer.append(" and m.type = ?");
			args.add(meter.getType());
		}
		if (meter.getEnergyType() != null){
			selectSqlBuffer.append(" and m.energy_type like ?");
			args.add("%"+meter.getEnergyType()+"%");
		}
		if (StringUtil.hasText(meter.getBuildingAreaId())){
			selectSqlBuffer.append(" and m.building_area_id = ?");
			args.add(meter.getBuildingAreaId());
		}
		Page<Meter> queryPage = baseJdbcDao.queryPage(pageRequest, selectSqlBuffer.toString(), args.toArray(), Meter.class);
		return queryPage;
	}
	
	

}

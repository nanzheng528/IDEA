package com.freedoonline.domain.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.controller.MeterController;
import com.freedoonline.domain.MeterDao;
import com.freedoonline.domain.entity.Meter;
import com.freedoonline.service.bo.MeterBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.utils.StringUtil;

/**
  * 
  *<p>类名：MeterDaoImpl.java</p>
  * @Descprition 表计DAO实现类
  * @author 南征
  * @version 1.0
  * @since JDK1.8
  *<p>创建日期：下午1:46:10</p>
  */
@Repository
public class MeterDaoImpl implements MeterDao {

	private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
	/**
	 * fieldType: int
	 * field: RUNSTATUS
	 * @Description 运行中状态
	 */
	private static final int RUNSTATUS = 1;
	
	/**
	 * fieldType: int
	 * field: UNRUNSTATUS
	 * @Description 未运行状态
	 */
	private static final int UNRUNSTATUS = 0;
	/**
	 * fieldType: int
	 * field: ACTIVE
	 * @Description 有效状态
	 */
	private static final int ACTIVE = 1;
	
	/**
	 * fieldType: int
	 * field: UNACTIVE
	 * @Description 无效状态
	 */
	private static final int UNACTIVE = 0;

	static String INSERTMETERSQL;
	static String DELTMETERSQL;
	static String UPDATESQL;
	static String SELECTSQL;
	static String SELECTBUIDINGAREASQL;

	static {
		INSERTMETERSQL = "INSERT INTO meter (object_id, building_id, building_area_id, name, number, type, energy_type,unit, "
				+ "service_area,ul_alarm, ll_alarm,status, create_user, create_time, modify_user, modify_time, active, remark)VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		DELTMETERSQL = "update meter SET active = ?,modify_user = ?,modify_time = ? where object_id = ?";
		UPDATESQL = "update meter SET name  = ?,number = ?,type = ?,energy_type = ?,unit = ?,building_area_id = ?,ul_alarm =?,ll_alarm = ?,status = ?,"
				+ "modify_user = ?,modify_time = ?" + " where object_id = ? ";
		SELECTSQL = "select m.number, m.name,m.building_id,m.unit as unit,u.value as unit_name, m.type as type,m.ul_alarm,m.ll_alarm,t.value as type_name, m.building_area_id,m.energy_type,e.value as energy_name , m.status , m.object_id ,a.area_name as building_area_name , b.enp_id,m.remark from meter m left join  building b on m.building_id = b.object_id left join (select code ,value , value_en from domain_table where domain_name = 'meter_type') t on t.code = m.type  left join (select code,value ,value_en from domain_table where domain_name = 'ec_type_1' or domain_name = 'ec_type_2') e on e.code = m.energy_type left join building_area a on a.object_id = m.building_area_id  left join (select code ,value ,value_en from domain_table where domain_name = 'unit') u  on  u.code = m.unit  where b.enp_id = ? and m.active = '1'";
		SELECTBUIDINGAREASQL = "select object_id , area_name from  building_area where 1 = 1 "; 
	}

	/** 
	* @Title: addMeter 
	* @Description 添加表计
	* @param meter
	* @return 
	* @author 南征
	* @date 2018年6月14日下午1:52:08
	*/ 
	public String addMeter(Meter meter) {
		String objectId = (!StringUtil.hasText(meter.getObjectId())) ? UUID.randomUUID().toString()
				: meter.getObjectId();
		Object[] args = { objectId, meter.getBuildingId(), meter.getBuildingAreaId(), meter.getName(),StringUtil.hasText(meter.getNumber()) == true ? meter.getNumber() : objectId, 
				meter.getType(), meter.getEnergyType(), meter.getUnit(), meter.getServiceArea(),
				meter.getUlAlarm(), meter.getLlAlarm(), meter.getStatus() != null ? meter.getStatus() : 1,
				meter.getCreateUser(), meter.getCreateTime() != null ? meter.getCreateTime() : new Date(),
				meter.getModifyUser(), meter.getModifyTime() != null ? meter.getModifyTime() : new Date(),
				meter.getActive() != null ? meter.getActive() : ACTIVE, meter.getRemark() };
		baseJdbcDao.save(INSERTMETERSQL, args);
		logger.info("-----------meterdao插入数据成功--------------");
		return objectId;

	}

	/** 
	* @Title: 删除表计
	* @Description 删除表计信息
	* @param map
	* @return 
	* @author 南征
	* @date 2018年6月14日下午1:51:36
	*/ 
	@Override
	public int delMeter(Map<String, Object> map) {
		Object[] args = { UNACTIVE, map.get("modifyUser"), new Date(), map.get("objectId"), };
		return baseJdbcDao.update(DELTMETERSQL, args);

	}

	/** 
	* @Title: updateMeter 
	* @Description 更新表计信息
	* @param meter
	* @return 
	* @author 南征
	* @date 2018年6月14日下午1:51:03
	*/ 
	@Override
	public int updateMeter(Meter meter) {
		Object[] args = { meter.getName(), meter.getNumber() , meter.getType(), meter.getEnergyType(), meter.getUnit(),
				meter.getBuildingAreaId(), meter.getUlAlarm(), meter.getLlAlarm(),
				meter.getStatus() != null ? meter.getStatus() : RUNSTATUS, meter.getModifyUser(),
				meter.getModifyTime() != null ? meter.getModifyTime() : new Date(), meter.getObjectId()
					};
		return baseJdbcDao.update(UPDATESQL, args);
	}

	/** 
	* @Title: queryMeterData 
	* @Description 查询表计列表信息
	* @param pageRequest 分页信息
	* @param meter 
	* @return 
	* @author 南征
	* @date 2018年6月14日下午1:50:31
	*/ 
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

	/** 
	* @Title: queryPosition 
	* @Description 查询区域位置信息
	* @param searchMap
	* @return List
	* @author 南征
	* @date 2018年6月14日下午1:48:08
	*/ 
	@SuppressWarnings("rawtypes")
	@Override
	public List queryPosition(Map<String, Object> searchMap) {
		//查询条件
		ArrayList<Object> args = new ArrayList<>();
		//查询语句
		StringBuffer stringBufferSql = new StringBuffer(SELECTBUIDINGAREASQL);
		//如果没有传入objectId 怎查询所有父节点
		if(!StringUtil.hasText((String)searchMap.get("objectId"))){
			stringBufferSql.append(" and parent_id is null");
		}
		//查询传入父节点下的所有子节点
		if(StringUtil.hasText((String)searchMap.get("objectId"))){
			stringBufferSql.append(" and parent_id = ?");
			args.add(searchMap.get("objectId"));
		}
		return baseJdbcDao.queryForListMap(stringBufferSql.toString(),args.toArray() );
	}
}

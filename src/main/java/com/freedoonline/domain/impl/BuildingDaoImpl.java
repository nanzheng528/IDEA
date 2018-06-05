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

import com.freedoonline.domain.BuildingDao;
import com.freedoonline.domain.entity.Building;
import com.freedoonline.domain.entity.BuildingArea;
import com.freedoonline.domain.entity.Equipment;
import com.freedoonline.service.bo.BuildingAreaBo;
import com.freedoonline.service.bo.BuildingFloorBo;
import com.freedoonline.service.bo.EquipmentBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.utils.StringUtil;

@Repository
public class BuildingDaoImpl implements BuildingDao {
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
	static String SELECT_BUILDING_SQL = "";
	static String INSERT_BUILDING_AREA_SQL = "";
	static String SELECT_BUILDING_AREA_SQL = "";
	
	static {
		SELECT_BUILDING_SQL = " SELECT b.* from building b LEFT JOIN user u on b.enp_id=u.enp_id and b.active=1 and u.active=1 ";
		INSERT_BUILDING_AREA_SQL = " INSERT INTO building_area (object_id, area_num, area_name, area_type, number, floor, purpose, remark, enp_id, create_uesr, create_time, modify_user, modify_time, active, building_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		SELECT_BUILDING_AREA_SQL = " select ba.object_id, ba.building_id,ba.area_name, ba.area_num, ba.area_type, ba.floor, ba.number, ba.purpose, ba.remark, dt.value as areaTypeCn, dt.value_en AS areaTypeEn from building_area ba LEFT JOIN building b ON ba.building_id=b.object_id AND ba.active=1 AND b.active=1 LEFT JOIN domain_table dt ON dt.code=ba.area_type AND dt.domain_name='building_area_type' AND dt.active=1 ";
	}
	
	@Override
	public List<Building> infoAll(String objectId) {
		StringBuffer buffer = new StringBuffer(SELECT_BUILDING_SQL);
		buffer.append(" where u.object_id=? ");
		Object[] args = {objectId};
		@SuppressWarnings("unchecked")
		List<Building> resultList = baseJdbcDao.queryForList(buffer.toString(), args, Building.class);
		return resultList;
	}
	
	@Override
	public Object info(String buildingId,String enpId) {
		StringBuffer buffer = new StringBuffer(SELECT_BUILDING_SQL);
		buffer.append(" where b.object_id=? and b.enp_id = ?");
		Object[] args = {buildingId,enpId};
		@SuppressWarnings("unchecked")
		List<Building> resultList = baseJdbcDao.queryForList(buffer.toString(), args, Building.class);
		if(resultList!=null){
			return resultList.get(0);
		}
		return null;
	}
	
	@Override
	public Object queryBuildingFloor(String buildingId, String enpId) {
		String sql = " SELECT b.object_id,group_concat( distinct  e.building_floor SEPARATOR ',') building_floor,b.building_name FROM equipment e LEFT JOIN building b ON e.building_id=b.object_id AND e.active=1 AND b.active=1 WHERE b.object_id=? AND b.enp_id=? ";
		Object[] args = {buildingId,enpId};
		return  baseJdbcDao.queryForList(sql, args, BuildingFloorBo.class);
	}
	
	/**
	  * 
	  * <p>功能描述:增加楼宇区域。</p>	
	  * @param buildingArea
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年5月29日 下午3:55:19。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object addBuildingArea(BuildingArea buildingArea) {
		String objectId = StringUtil.hasText(buildingArea.getObjectId())?buildingArea.getObjectId():UUID.randomUUID().toString();
		//object_id, area_num, area_name, area_type, number, floor, purpose, 
		//remark, enp_id, create_uesr, create_time, modify_user, modify_time, active
		Object[] args =  {
			objectId, buildingArea.getAreaNum(),buildingArea.getAreaName(),buildingArea.getAreaType(),
			buildingArea.getNumber()!=null?buildingArea.getNumber():1,buildingArea.getFloor(),buildingArea.getPurpose(),
			buildingArea.getRemark(),buildingArea.getEnpId(),buildingArea.getCreateUser(),
			buildingArea.getCreateTime()!=null?buildingArea.getCreateTime():new Date(),buildingArea.getModifyUser(),
			buildingArea.getModifyTime()!=null?buildingArea.getModifyTime():new Date(),
			buildingArea.getActive()!=null?buildingArea.getActive():1,buildingArea.getBuildingId()
		};
		baseJdbcDao.save(INSERT_BUILDING_AREA_SQL, args);
		return objectId;
	}
	
	@Override
	public Object queryBuildingArea(PageRequest pageRequest,BuildingArea buildingArea) {
		StringBuffer buffer = new StringBuffer(SELECT_BUILDING_AREA_SQL);
		buffer.append(" where 1=1 ");
		Map<String, Object> map = getQueryCondition(buildingArea);
		buffer.append(map.get("where")); // 拼接条件语句
		Object[] args = (Object[]) map.get("args");
		Page<BuildingAreaBo> resultPage = baseJdbcDao.queryPage(pageRequest, buffer.toString(), args, BuildingAreaBo.class);
		return resultPage;
	}
	
	private Map<String, Object> getQueryCondition(BuildingArea queryBo) {
		// 拼接查询条件
		StringBuffer whereBuffer = new StringBuffer();
		List<Object> args = new ArrayList<Object>();

		if (StringUtil.hasText(queryBo.getBuildingId())) {
			// 楼宇ID
			whereBuffer.append(" and ba.building_id = ? ");
			args.add(queryBo.getBuildingId());
		}
		if (StringUtil.hasText(queryBo.getEnpId())) {
			// 企业ID
			whereBuffer.append(" and b.enp_id = ? ");
			args.add(queryBo.getEnpId());
		}
		if (StringUtil.hasText(queryBo.getObjectId())) {
			// 明细查询
			whereBuffer.append(" and ba.object_id= ? ");
			args.add(queryBo.getObjectId());
		}
		if (StringUtil.hasText(queryBo.getAreaNum())) {
			// 区域编号
			whereBuffer.append(" and ba.area_num like ? ");
			args.add("%" + queryBo.getAreaNum() + "%");
		}
		if (StringUtil.hasText(queryBo.getAreaName())) {
			// 区域名称
			whereBuffer.append(" and ba.area_name like ? ");
			args.add("%" + queryBo.getAreaName() + "%");
		}
		if(queryBo.getAreaType()!=null){
			if (StringUtil.hasText(queryBo.getAreaType().toString())) {
				// 区域类型
				whereBuffer.append(" and ba.area_type = ? ");
				args.add(queryBo.getAreaType());
			}
		}
		if (StringUtil.hasText(queryBo.getFloor())) {
			// 楼层
			whereBuffer.append(" and ba.floor like ? ");
			args.add("%" + queryBo.getFloor() + "%");
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("where", whereBuffer.toString());
		resultMap.put("args", args.toArray());
		return resultMap;
	}
	
	@Override
	public BuildingArea queryBaById(String objectId, String enpId) {
		StringBuffer buffer = new StringBuffer(SELECT_BUILDING_AREA_SQL);
		buffer.append(" WHERE ba.object_id=? AND ba.enp_id=? AND ba.active=1 ");
		Object[] args = {objectId,enpId};
		return (BuildingArea)baseJdbcDao.queryForObject(buffer.toString(), args, BuildingArea.class);
	}
}

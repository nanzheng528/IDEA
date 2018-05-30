package com.freedoonline.domain;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Building;
import com.freedoonline.domain.entity.BuildingArea;

import cn.cloudlink.core.common.dataaccess.data.PageRequest;

public interface BuildingDao {
	// 查询所有楼宇信息
	public List<Building> infoAll(String objectId);
	// 根据ID查楼宇
	public Object info(String buildingId,String enpId);
	
	public Object queryBuildingFloor(String buildingId,String enpId);
	// 增加楼宇区域
	public Object addBuildingArea(BuildingArea buildingArea);
	// 查询楼宇区域列表
	public Object queryBuildingArea(PageRequest pageRequest,BuildingArea buildingArea);
}

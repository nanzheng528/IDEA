package com.freedoonline.service;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Building;
import com.freedoonline.domain.entity.BuildingArea;

import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

public interface BuildingService {
	// 查询所有楼宇信息
	public List<Building> infoAll(String objectId)throws BusinessException,Exception;
	// 根据楼宇ID查询楼宇信息
	public Object info(String buildingId,String enpId) throws BusinessException,Exception;
	// 查询楼宇的楼层
	public Object queryBuildingFloor(String buildingId,String enpId) throws BusinessException,Exception;
	// 增加楼宇区域
	public Object addBuildingArea(BuildingArea buildingArea) throws BusinessException,Exception;
	// 查询楼宇区域列表
	public Object queryBuildingArea(PageRequest pageRequest,BuildingArea buildingArea) throws BusinessException,Exception;
	
}

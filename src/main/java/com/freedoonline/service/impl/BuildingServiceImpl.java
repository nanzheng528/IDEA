package com.freedoonline.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.domain.BuildingDao;
import com.freedoonline.domain.entity.Building;
import com.freedoonline.domain.entity.BuildingArea;
import com.freedoonline.service.BuildingService;
import com.freedoonline.service.bo.BuildingFloorBo;
import com.freedoonline.service.bo.EnergyBo;

import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service("buildingServiceImpl")
public class BuildingServiceImpl implements BuildingService {
	
	@Resource(name = "buildingDaoImpl")
	private BuildingDao buildingDao;

	@Override
	public List<Building> infoAll(String objectId) throws BusinessException, Exception {
		return buildingDao.infoAll(objectId);
	}
	
	public Object info(String buildingId,String enpId) throws BusinessException,Exception{
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return buildingDao.info(buildingId,enpId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object queryBuildingFloor(String buildingId, String enpId) throws BusinessException, Exception {
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		List<BuildingFloorBo> list = (List) buildingDao.queryBuildingFloor(buildingId,enpId);
		Map <String ,Object> map = new HashMap<>();
		if(null!=list && list.size()>0){
			String [] floor = list.get(0).getBuildingFloor().split(",");
			List<String> resultList= new ArrayList<>(Arrays.asList(floor));
			Comparator<String> comparator = new Comparator<String>() {
				public int compare(String s1, String s2) {
					if (s1 != s2) {
						return s1.hashCode() - (s2.hashCode());
					} 
					return -1;
				}
			};
			Collections.sort(resultList,comparator);
			map.put("floor", resultList);
		}
		return map;
	}
	
	@Override
	public Object addBuildingArea(BuildingArea buildingArea) throws BusinessException, Exception {
		if (!StringUtil.hasText(buildingArea.getAreaName())) {
			throw new BusinessException("区域名称不能为空！", "403");
		}
		if (!StringUtil.hasText(buildingArea.getBuildingId())) {
			throw new BusinessException("所属楼宇不能为空！", "403");
		}
		if (!StringUtil.hasText(buildingArea.getAreaNum())) {
			throw new BusinessException("区域编号不能为空！", "403");
		}
		if (!StringUtil.hasText(buildingArea.getFloor())) {
			throw new BusinessException("区域位置不能为空！", "403");
		}
		if (!StringUtil.hasText(buildingArea.getAreaType().toString())) {
			throw new BusinessException("区域类型不能为空！", "403");
		}
		return buildingDao.addBuildingArea(buildingArea);
	}
	
	/**
	  * 
	  * <p>功能描述:获取楼宇的所有区域。</p>	
	  * @param param
	  * @return
	  * @throws BusinessException
	  * @throws Exception
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年5月29日 下午5:37:47。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object queryBuildingArea(PageRequest pageRequest,BuildingArea buildingArea) throws BusinessException, Exception {
		if (!StringUtil.hasText(buildingArea.getBuildingId())) {
			throw new BusinessException("区域名称不能为空！", "403");
		}
		return buildingDao.queryBuildingArea(pageRequest,buildingArea);
	}
}

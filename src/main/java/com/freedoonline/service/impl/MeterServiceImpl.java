package com.freedoonline.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freedoonline.controller.MeterController;
import com.freedoonline.domain.MeterDao;
import com.freedoonline.domain.entity.Meter;
import com.freedoonline.service.MeterService;
import com.google.inject.grapher.graphviz.EdgeStyle;

import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service
public class MeterServiceImpl implements MeterService {
	
	private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
	
	@Resource
	private MeterDao MeterDao;
	
	@Override
	public String addMeter(Meter meter) {
		logger.info("--------------开始添加meter数据----------------");
		if (!StringUtil.hasText(meter.getBuildingId())) {
			throw new BusinessException("所属楼宇不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getBuildingAreaId())) {
			throw new BusinessException("位置不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getName())) {
			throw new BusinessException("表计名称不能为空！", "403");
		}
		if (null == meter.getUnit()) {
			throw new BusinessException("单位不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getNumber())) {
			throw new BusinessException("表计编号不能为空！", "403");
		}
		if (null == meter.getType()) {
			throw new BusinessException("表计类型不能为空！", "403");
		}
		if (null == meter.getEnergyType()) {
			throw new BusinessException("能耗类别不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getServiceArea())){
			throw new BusinessException("服务区域不能为空！", "403");
		}
		String Objectid = MeterDao.addMeter(meter);
		logger.info("-------添加meter数据成功----------");
		return Objectid;
	}

	@Override
	public Boolean delMeter(Map<String, Object> map) throws BusinessException, Exception {
		logger.info("--------------开始删除meter数据----------------");
		if (!StringUtil.hasText((String)map.get("objectId"))){
			throw new BusinessException("objectId不能为空", "403");
		}
		if (MeterDao.delMeter(map) == 1){
			return true;
		};
		return false;
	}

	@Override
	public int validMeterId(String objectId) throws BusinessException, Exception {
		return 0;
	}

	@Override
	public Boolean updateMeter(Meter meter) throws BusinessException, Exception {
		logger.info("--------------开始更新meter数据----------------");
		if (!StringUtil.hasText(meter.getObjectId())){
			throw new BusinessException("objectId不能为空", "403");
		}
		if (!StringUtil.hasText(meter.getBuildingId())) {
			throw new BusinessException("所属楼宇不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getBuildingAreaId())) {
			throw new BusinessException("位置不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getName())) {
			throw new BusinessException("表计名称不能为空！", "403");
		}
		if (null == meter.getUnit()) {
			throw new BusinessException("单位不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getNumber())) {
			throw new BusinessException("表计编号不能为空！", "403");
		}
		if (null == meter.getType()) {
			throw new BusinessException("表计类型不能为空！", "403");
		}
		if (null == meter.getEnergyType()) {
			throw new BusinessException("能耗类别不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getServiceArea())){
			throw new BusinessException("服务区域不能为空！", "403");
		}
		int result = MeterDao.updateMeter(meter);
		if (result == 1){
			return true;
		} else {
			return false;
		}
	}

}

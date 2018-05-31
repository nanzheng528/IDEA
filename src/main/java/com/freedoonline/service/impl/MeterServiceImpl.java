package com.freedoonline.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freedoonline.controller.MeterController;
import com.freedoonline.domain.MeterDao;
import com.freedoonline.domain.entity.Meter;
import com.freedoonline.service.MeterService;

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
			throw new BusinessException("所属楼宇区域不能为空！", "403");
		}
		if (!StringUtil.hasText(meter.getName())) {
			throw new BusinessException("表计名称不能为空！", "403");
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

}

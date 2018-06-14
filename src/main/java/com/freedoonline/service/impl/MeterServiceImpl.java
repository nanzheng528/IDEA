package com.freedoonline.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.freedoonline.controller.MeterController;
import com.freedoonline.domain.MeterDao;
import com.freedoonline.domain.entity.Meter;
import com.freedoonline.service.MeterService;
import com.freedoonline.service.bo.MeterBo;
import com.netflix.infix.lang.infix.antlr.EventFilterParser.null_predicate_return;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service
public class MeterServiceImpl implements MeterService {
	
	private static final Logger logger = LoggerFactory.getLogger(MeterController.class);
	
	@Resource
	private MeterDao MeterDao;
	
	/** 
	* @Title: addMeter 
	* @Description 添加表计服务
	* @param meter
	* @return 
	* @author 南征
	* @date 2018年6月14日下午2:12:45
	*/ 
	@Override
	public String addMeter(Meter meter) {
		logger.info("--------------开始添加meter数据----------------");
//		if (!StringUtil.hasText(meter.getBuildingId())) {
//			throw new BusinessException("所属楼宇不能为空！", "403");
//		}
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
		if (null == meter.getUlAlarm()) {
			throw new BusinessException("上限警报值不能为空！", "403");
		}if (null == meter.getLlAlarm()) {
			throw new BusinessException("下限警报值不能为空！", "403");
		}
//		if (!StringUtil.hasText(meter.getServiceArea())){
//			throw new BusinessException("服务区域不能为空！", "403");
//		}
		String Objectid = MeterDao.addMeter(meter);
		logger.info("-------添加meter数据成功----------");
		return Objectid;
	}

	/** 
	* @Title: delMeter 
	* @Description 删除表计服务
	* @param map
	* @return
	* @throws BusinessException
	* @throws Exception 
	* @author 南征
	* @date 2018年6月14日下午2:12:56
	*/ 
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

	/** 
	* @Title: updateMeter 
	* @Description 更新表计
	* @param meter
	* @return
	* @throws BusinessException
	* @throws Exception 
	* @author 南征
	* @date 2018年6月14日下午2:15:57
	*/ 
	@Override
	public Boolean updateMeter(Meter meter) throws BusinessException, Exception {
		logger.info("--------------开始更新meter数据----------------");
		if (!StringUtil.hasText(meter.getObjectId())){
			throw new BusinessException("objectId不能为空", "403");
		}
//		if (!StringUtil.hasText(meter.getBuildingId())) {
//			throw new BusinessException("所属楼宇不能为空！", "403");
//		}
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
		if (null == meter.getUlAlarm()) {
			throw new BusinessException("上限警报值不能为空！", "403");
		}if (null == meter.getLlAlarm()) {
			throw new BusinessException("下限警报值不能为空！", "403");
		}
//		if (!StringUtil.hasText(meter.getServiceArea())){
//			throw new BusinessException("服务区域不能为空！", "403");
//		}
		int result = MeterDao.updateMeter(meter);
		if (result == 1){
			
			return true;
		} else {
			return false;
		}
	}

	/** 
	* @Title: queryMeter 
	* @Description 查询表计
	* @param pageRequest
	* @param meter
	* @return 
	* @author 南征
	* @date 2018年6月14日下午2:15:40
	*/ 
	@Override
	public Page<Meter> queryMeter(PageRequest pageRequest, MeterBo meter) {
		logger.info("--------------开始查询meter数据业务----------------");
		return MeterDao.queryMeterData(pageRequest, meter);
	}

	/** 
	* @Title: queryPosition 
	* @Description 查询区域位置信息
	* @param searchMap
	* @return 
	* @author 南征
	* @date 2018年6月14日下午2:13:22
	*/ 
	@SuppressWarnings("unchecked")
	@Override
	public List queryPosition(Map<String,Object> searchMap) {
		List<Map<String, Object>> positionList = MeterDao.queryPosition(searchMap);
		//遍历当前树节点
		for(Map<String, Object> map : positionList){
			//查询当前节点下是否有子节点
			List<Map<String, Object>> leafList = MeterDao.queryPosition(map);
			// 当前为叶子节点
			if(null == leafList || leafList.size() < 1){
				map.put("leaf", true);
			} else {
			// 当前不是叶子节点
			map.put("leaf", false);
			}
		}
		return positionList;
	}


}

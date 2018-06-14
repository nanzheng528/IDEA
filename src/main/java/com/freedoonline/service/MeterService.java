package com.freedoonline.service;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Meter;
import com.freedoonline.service.bo.MeterBo;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;

public interface MeterService {
	//添加表计
	public String addMeter(Meter meter);
	//删除表计
	public Boolean delMeter(Map<String, Object> map) throws BusinessException,Exception;
	//表计是否有效
	public int validMeterId(String objectId) throws BusinessException,Exception;
	//更新表计
	public Boolean updateMeter(Meter meter) throws BusinessException,Exception;
	//查询表计
	public Page<Meter> queryMeter(PageRequest pageRequest,MeterBo meter);
	//查询位置信息
	public List queryPosition(Map<String, Object> searchMap);
}

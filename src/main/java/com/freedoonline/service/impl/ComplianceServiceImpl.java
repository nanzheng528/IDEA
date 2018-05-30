package com.freedoonline.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.common.util.TransformUtil;
import com.freedoonline.domain.ComplianceDao;
import com.freedoonline.service.ComplianceService;
import com.freedoonline.service.ICommonService;
import com.freedoonline.service.bo.BuildingLicenseBo;

import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service("complianceServiceImpl")
public class ComplianceServiceImpl implements ComplianceService {
	
	@Resource(name = "complianceDaoImpl")
	private ComplianceDao complianceDao;
	
	@Resource(name = "commonServiceImpl")
	private ICommonService commonService;
	
	@Override
	public Object maintenace(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if(!StringUtil.hasText(buildingId)){
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return complianceDao.maintenace(param);
	}
	
	@Override
	public Object buildingLicense(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if(!StringUtil.hasText(buildingId)){
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return complianceDao.buildingLicense(param);
	}
	
	@Override
	public Object equipmentAuth(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if(!StringUtil.hasText(buildingId)){
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return complianceDao.equipmentAuth(param);
	}
	
	@Override
	public Object pushMsg(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if(!StringUtil.hasText(buildingId)){
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return complianceDao.pushMsg(param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object auth(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		Integer authType = (Integer)param.get("authType");
		String objectId = (String)param.get("objectId");
		
//		Long endTime1 = (Long)param.get("endTime");
//		Date endTime = new Date();
//		if(endTime1!=null){
//			Date endTime2= new Date(endTime1);
//			endTime = endTime2;
//		}
		//Date endTime= new Date(endTime1);
		
		//更新
		Integer authStatus = (Integer)param.get("authStatus");
		
		if(!StringUtil.hasText(buildingId)){
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		if(!StringUtil.hasText(authType.toString())){
			throw new BusinessException("认证类型不能为空！", "403");
		}
		// 判断当前楼宇有没有认证该楼宇
		if(!StringUtil.hasText(objectId)){
//			Long endTime1 = (Long)param.get("endTime");
//			Date endTime = new Date();
//			if(endTime1!=null){
//				Date endTime2= new Date(endTime1);
//				endTime = endTime2;
//			}else{
//				throw new BusinessException("认证结束时间不能为空！", "403");
//			}
			
			if(authType.toString().substring(0, 1).equals("1")){
				List<BuildingLicenseBo> bList = (List<BuildingLicenseBo>)complianceDao.authBuilding(authType.toString(), buildingId);
				if(bList!=null && bList.size()>0){
					throw new BusinessException("重复的认证！", "403");
				}else{
					return complianceDao.auth(param,"1");
				}
			}else if(authType.toString().substring(0, 1).equals("2")){
				List<BuildingLicenseBo> uList = (List<BuildingLicenseBo>)complianceDao.authUser(authType.toString(), buildingId);
				if(uList!=null && uList.size()>0){
					throw new BusinessException("重复的认证！", "403");
				}else{
					return complianceDao.auth(param,"2");
				}
			}else if(authType.toString().substring(0, 1).equals("3")){
				List<BuildingLicenseBo> eList = (List<BuildingLicenseBo>)complianceDao.authEquipment(authType.toString(), buildingId);
				if(eList!=null && eList.size()>0){
					throw new BusinessException("重复的认证！", "403");
				}else{
					return complianceDao.auth(param,"3");
				}
			}else{
				throw new BusinessException("认证类型不合理！", "403");
			}
		}else{
//			Long endTime1 = (Long)param.get("endTime");
//			Date endTime = new Date();
//			if(endTime1!=null){
//				Date endTime2= new Date(endTime1);
//				endTime = endTime2;
//			}else{
//				endTime = null;
//			}
			
			Map<String, Object> resultMap = new HashMap<>();
			if(authType.toString().substring(0, 1).equals("1")){
				if(StringUtil.hasText(authStatus.toString())){
					resultMap.put("authStatus", authStatus);
				}
//				if(endTime!=null){
//					resultMap.put("endTime", endTime);
//				}
				List<String> columnNames = new ArrayList<String>();
				List<Object> columnValues = new ArrayList<Object>();
				for (String key : resultMap.keySet()) {
					columnNames.add(TransformUtil.humpToLine2(key)); // 将驼峰命名转换为下划线
					columnValues.add(resultMap.get(key));
				}
				columnNames.add("modify_time");
				columnValues.add(new Date());
				this.update(columnNames.toArray(new String[columnNames.size()]), columnValues.toArray(),
						new String[] { "object_id" }, new Object[] { objectId }, null,"building_auth_ref");
				
			}else if(authType.toString().substring(0, 1).equals("2")){
				if(StringUtil.hasText(authStatus.toString())){
					resultMap.put("authStatus", authStatus);
				}
//				if(endTime!=null){
//					resultMap.put("endTime", endTime);
//				}
				List<String> columnNames = new ArrayList<String>();
				List<Object> columnValues = new ArrayList<Object>();
				for (String key : resultMap.keySet()) {
					columnNames.add(TransformUtil.humpToLine2(key)); // 将驼峰命名转换为下划线
					columnValues.add(resultMap.get(key));
				}
				columnNames.add("modify_time");
				columnValues.add(new Date());
				this.update(columnNames.toArray(new String[columnNames.size()]), columnValues.toArray(),
						new String[] { "object_id" }, new Object[] { objectId }, null,"user_auth_ref");
				
			}else if(authType.toString().substring(0, 1).equals("3")){
				if(StringUtil.hasText(authStatus.toString())){
					resultMap.put("authStatus", authStatus);
				}
//				if(endTime!=null){
//					resultMap.put("endTime", endTime);
//				}
				List<String> columnNames = new ArrayList<String>();
				List<Object> columnValues = new ArrayList<Object>();
				for (String key : resultMap.keySet()) {
					columnNames.add(TransformUtil.humpToLine2(key)); // 将驼峰命名转换为下划线
					columnValues.add(resultMap.get(key));
				}
				columnNames.add("modify_time");
				columnValues.add(new Date());
				this.update(columnNames.toArray(new String[columnNames.size()]), columnValues.toArray(),
						new String[] { "object_id" }, new Object[] { objectId }, null,"equipment_auth_ref");
				
			}else{
				throw new BusinessException("认证类型不合理！", "403");
			}
		}
		return objectId;
	}
	
	public boolean update(String[] columnNames, Object[] columnValues, String[] whereNames, Object[] whereValues,
			String whereFilter,String tableName) throws BusinessException, Exception {
		boolean updateResult = commonService.updateColumns(tableName, columnNames, columnValues, whereNames, whereValues,
				whereFilter);
		return updateResult;
	}
	
	@Override
	public Object authScore(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if(!StringUtil.hasText(buildingId)){
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return complianceDao.authScore(param);
	}
}

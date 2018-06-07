package com.freedoonline.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.common.util.TransformUtil;
import com.freedoonline.domain.BuildingDao;
import com.freedoonline.domain.EquipmentDao;
import com.freedoonline.domain.entity.BuildingArea;
import com.freedoonline.domain.entity.Equipment;
import com.freedoonline.domain.entity.MaintenancePlan;
import com.freedoonline.domain.entity.SubSystemFault;
import com.freedoonline.domain.entity.SystemFault;
import com.freedoonline.service.EquipmentService;
import com.freedoonline.service.ICommonService;
import com.freedoonline.service.bo.EquipmentBo;
import com.freedoonline.service.bo.MaintenancePlanBo;
import com.freedoonline.service.bo.SubSystemBo;
import com.freedoonline.service.bo.SystemFaultBo;
import com.freedoonline.service.bo.SystemFaultPct;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service("equipmentServiceImpl")
public class EquipmentServiceImpl implements EquipmentService {
	
	@Resource(name = "equipmentDaoImpl")
	private EquipmentDao equipmentDao;
	
	@Resource(name = "commonServiceImpl")
	private ICommonService commonService;
	
	@Resource(name = "buildingDaoImpl")
	private BuildingDao buildingDao;
	
	@Override
	public Page<Equipment> queryListEquipment(PageRequest pageRequest,EquipmentBo queryBo) throws BusinessException, Exception {
		return equipmentDao.queryListEquipment(pageRequest,queryBo);
	}
	
	@Override
	public Object addEquipment(EquipmentBo queryBo) throws BusinessException, Exception {
//		if (!StringUtil.hasText(queryBo.getBuildingId())) {
//			throw new BusinessException("所属楼宇不能为空！", "403");
//		}
//		if (!StringUtil.hasText(queryBo.getBuildingFloor())) {
//			throw new BusinessException("所属楼层不能为空！", "403");
//		}
		if (!StringUtil.hasText(queryBo.getEquNum())) {
			throw new BusinessException("设备编号不能为空！", "403");
		}
		if (!StringUtil.hasText(queryBo.getEquName())) {
			throw new BusinessException("设备名称不能为空！", "403");
		}
//		if (!StringUtil.hasText(queryBo.getEquModel())) {
//			throw new BusinessException("设备型号不能为空！", "403");
//		}
//		if (!StringUtil.hasText(queryBo.getManufacturer())) {
//			throw new BusinessException("生产厂商不能为空！", "403");
//		}
//		if(null == queryBo.getQuantity()){
//			throw new BusinessException("设备数量不能为空！", "403");
//		}
		if (!StringUtil.hasText(queryBo.getBuildingAreaId())) {
			throw new BusinessException("区域ID不能为空！", "403");
		}
		BuildingArea buildingArea = buildingDao.queryBaById(queryBo.getBuildingAreaId(),queryBo.getEnpId());
		if(null!=buildingArea){
			queryBo.setBuildingFloor(buildingArea.getFloor());
			queryBo.setBuildingArea(buildingArea.getAreaName());
			queryBo.setBuildingId(buildingArea.getBuildingId());
		}else{
			throw new BusinessException("区域ID不存在！", "403");
		}
		return equipmentDao.addEquipment(queryBo);
	}
	
	@Override
	public Page<MaintenancePlan> queryListMaintenancePlan(PageRequest pageRequest, MaintenancePlanBo queryBo)
			throws BusinessException, Exception {
		Page<MaintenancePlan> resultPage = equipmentDao.queryListMaintenancePlan(pageRequest, queryBo);
		List<MaintenancePlan> list = resultPage.getResult();
		int Total = list.size();
		int backlog = 0;		//待办数量
		int complete = 0;		//完成数量
		int remainder = 0;		//剩余数量
		int overdue = 0;		//逾期数量
		if(null!=list){
			for(MaintenancePlan mp: list){
				Date date = new Date();
				//判断完成
				if(mp.getStatus()==1){
					if(mp.getStatus()==1){
						complete++;
					}
					//判断完成逾期
					if(mp.getStatus() == 1 && ( mp.getActualTime().getTime() > mp.getPlanEndTime().getTime() )){
						overdue++;
					}
				}else{
					//判断待办
					if((mp.getPlanStartTime().getTime() < date.getTime()) && (mp.getPlanEndTime().getTime() > date.getTime()) ){
						backlog++;
					}
					//判断剩余
					if((mp.getPlanStartTime().getTime() > date.getTime()) && (mp.getPlanEndTime().getTime() > date.getTime())){
						remainder++;
					}
					//判断未完成逾期
					if((mp.getActualTime() == null) && (date.getTime() > mp.getPlanEndTime().getTime())){
						overdue++;
					}
				}
//				if(mp.getStatus()==1){
//					complete++;
//				}
//				//判断未完成逾期
//				if((mp.getActualTime() == null) && (date.getTime() > mp.getPlanEndTime().getTime())){
//					overdue++;
//				}
//				//判断完成逾期
//				if(mp.getStatus() == 1 && ( mp.getActualTime().getTime() > mp.getPlanEndTime().getTime() )){
//					overdue++;
//				}
//				//判断待办
//				if((mp.getPlanStartTime().getTime() < date.getTime()) && (mp.getPlanEndTime().getTime() > date.getTime()) ){
//					backlog++;
//				}
//				//判断剩余
//				if((mp.getPlanStartTime().getTime() > date.getTime()) && (mp.getPlanEndTime().getTime() > date.getTime())){
//					remainder++;
//				}
			}
			
		}
		for(MaintenancePlan mp: list){
			mp.setBacklog(backlog);
			mp.setRemainder(remainder);
			mp.setOverdue(overdue);
			mp.setComplete(complete);
		}
		resultPage.setResult(list);
		return resultPage;
	}
	
	/**
	  * 
	  * <p>功能描述:增加维护计划。</p>	
	  * @param queryBo
	  * @return
	  * @throws BusinessException
	  * @throws Exception
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年5月5日 下午6:47:03。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object addMaintenancePlan(MaintenancePlanBo queryBo) throws BusinessException, Exception {
		
		if (!StringUtil.hasText(queryBo.getEquId())) {
			throw new BusinessException("设备ID不能为空！", "403");
		}
		if (!StringUtil.hasText(queryBo.getMaintenanceId())) {
			throw new BusinessException("维护着ID不能为空！", "403");
		}
		if (queryBo.getPlanStartTime()==null) {
			throw new BusinessException("开始时间不能为空！", "403");
		}
		if (queryBo.getPlanEndTime()==null) {
			throw new BusinessException("结束时间不能为空！", "403");
		}
		if (!StringUtil.hasText(queryBo.getName())) {
			throw new BusinessException("任务名称不能为空！", "403");
		}
		return equipmentDao.addMaintenancePlan(queryBo);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemFaultBo> systemFault(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		int totalFault = 0;
		int totalEquNum = 0;
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		//List<SystemFault> list = equipmentDao.systemFault(param);
		//获取各个系统下的设备总数
		List<SystemFaultBo> list = (List<SystemFaultBo>)equipmentDao.totalSystemEqu(buildingId);
		
		if(list!=null){
			for(int i =0;i<list.size();i++){
				if(list.get(i).getFault()==null){
					list.get(i).setTotal(0);
					list.get(i).setFaultRate(0);
					list.get(i).setFaultNum(0);
				}else{
					int fualtNum = 0 ;
					SystemFaultBo sf =list.get(i);
					String [] fualtStatus = sf.getFault().split(",");
					sf.setTotal(fualtStatus.length);
					for(String s:fualtStatus){
						if(s.equals("0") || s == "0"){
							fualtNum++;
							totalFault++;
						}
					}
					if(fualtNum==0){
						sf.setFaultNum(0);
						sf.setFaultRate(0);
					}else{
						sf.setFaultNum(fualtNum);
						sf.setFaultRate((int)Math.rint((fualtNum*100)/sf.getEquNum()));
					}
				}
			}
			for(int i =0;i<list.size();i++){
				SystemFaultBo sf =list.get(i);
				totalEquNum+=sf.getEquNum();
			}
			for(int i =0;i<list.size();i++){
				list.get(i).setTotalScore(100-((totalFault*100)/totalEquNum));
			}
		}
		return list;
	}
	
	@Override
	public List<SubSystemBo> querySub(String systemId, String enpId) throws BusinessException, Exception {
		if (!StringUtil.hasText(systemId)) {
			throw new BusinessException("系统编号不能为空！", "403");
		}
		if (!StringUtil.hasText(enpId)) {
			throw new BusinessException("企业ID不能为空！", "403");
		}
		return equipmentDao.querySub(systemId, enpId);
	}
	
	@Override
	public List<SubSystemFault> subSystemFault(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		String systemNum = (String)param.get("systemNum");
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		if (!StringUtil.hasText(systemNum)) {
			throw new BusinessException("系统编号不能为空！", "403");
		}
		List<SubSystemFault> list =  equipmentDao.subSystemFault(param);
		if(null!=list){
			for(int i=0;i<list.size();i++){
				if(list.get(i).getFault()==null || list.get(i).getEquLevel()==null){
					list.get(i).setTotal(0);
					list.get(i).setFaultRate(0);
					list.get(i).setFaultNum(0);
					//list.get(i).setEquLevel(null);
					list.get(i).setEquLevelNum(0);
				}else{
					int fualtNum = 0 ;
					int equLevelNum = 0;
					SubSystemFault sf =list.get(i);
					String [] fualtStatus = sf.getFault().split(",");
					String [] levelStatus = sf.getEquLevel().split(",");
					sf.setTotal(fualtStatus.length==levelStatus.length?levelStatus.length:0);
					
//					for(int j=0;j<levelStatus.length;j++){
////						if(fualtStatus[j].equals("0")){
////							if(levelStatus[j].equals("1")){
////								fualtNum++;
////								equLevelNum++;
////							}else{
////								fualtNum++;
////							}
////							
////						}
//						for(String s:fualtStatus){
//							if(s.equals("0")){
//								if(levelStatus[j].equals("1")){
//									fualtNum++;
//									equLevelNum++;
//								}else{
//									fualtNum++;
//								}
//							}
//						}
//					}
					
					
						for(int j=0;j<fualtStatus.length;j++){
							if(fualtStatus[j].equals("0")){
								if(levelStatus[j].equals("1")){
									fualtNum++;
									equLevelNum++;
								}else{
									fualtNum++;
								}
							}
						}
					
					
					if(fualtNum==0){
						sf.setFaultNum(0);
						sf.setFaultRate(0);
						sf.setEquLevelNum(0);
						//sf.setEquLevel(null);
					}else{
						sf.setFaultNum(fualtNum);
						sf.setFaultRate((int)Math.rint((fualtNum*100)/levelStatus.length));
						sf.setEquLevelNum(equLevelNum);
					}
//					for(String s:fualtStatus){
//						if(s.equals("0") || s == "0"){
//							fualtNum++;
//						}
//					}
//					if(fualtNum==0){
//						sf.setFaultNum(0);
//						sf.setFaultRate(0);
//					}else{
//						sf.setFaultNum(fualtNum);
//						sf.setFaultRate((int)Math.rint((fualtNum*100)/fualtStatus.length));
//					}
				}
			}
		}
		return list;
	}
	
	@Override
	public List<SystemFaultPct> equSystemPct(Map<String, Object> param) throws BusinessException, Exception {
		List<SystemFaultPct> equSystemPct = new ArrayList<>();
		String buildingId = (String)param.get("buildingId");
		String systemNum = (String)param.get("systemNum");
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		if (!StringUtil.hasText(systemNum)) {
			throw new BusinessException("系统编号不能为空！", "403");
		}
		List<MaintenancePlan> list = equipmentDao.equSystemPct(param);
		if(null!=list && list.size()!=0){
			int sum = list.size();
			int L1 = 0;
			int L2 = 0;
			int L3 = 0;
			for(int i=0;i<list.size();i++){
				if(list.get(i).getEquLevel()==1){
					L1++;
				}else if(list.get(i).getEquLevel()==2){
					L2++;
				}else{
					L3++;
				}
			}
			SystemFaultPct systemFaultPct1 = new SystemFaultPct();
			systemFaultPct1.setName("高风险");
			systemFaultPct1.setValue((int)Math.rint((L1*100)/sum));
			SystemFaultPct systemFaultPct2 = new SystemFaultPct();
			systemFaultPct2.setName("中风险");
			systemFaultPct2.setValue((int)Math.rint((L2*100)/sum));
			SystemFaultPct systemFaultPct3 = new SystemFaultPct();
			systemFaultPct3.setName("低风险");
			systemFaultPct3.setValue(100-(int)Math.rint((L2*100)/sum)-(int)Math.rint((L1*100)/sum));
			equSystemPct.add(systemFaultPct1);
			equSystemPct.add(systemFaultPct2);
			equSystemPct.add(systemFaultPct3);
		}else{
			SystemFaultPct systemFaultPct1 = new SystemFaultPct();
			systemFaultPct1.setName("高风险");
			systemFaultPct1.setValue(0);
			SystemFaultPct systemFaultPct2 = new SystemFaultPct();
			systemFaultPct2.setName("中风险");
			systemFaultPct2.setValue(0);
			SystemFaultPct systemFaultPct3 = new SystemFaultPct();
			systemFaultPct3.setName("低风险");
			systemFaultPct3.setValue(0);
			equSystemPct.add(systemFaultPct1);
			equSystemPct.add(systemFaultPct2);
			equSystemPct.add(systemFaultPct3);
			return equSystemPct;
		}
		return equSystemPct;
	}
	
	@Override
	public Object pushMsg(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return equipmentDao.pushMsg(param);
	}
	
	@Override
	public Object faultRate(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return equipmentDao.faultRate(param);
	}
	
	@Override
	public String updateEquById(Map<String, Object> paramMap) throws BusinessException, Exception {
		String objectId = (String) paramMap.get("objectId");
		String enpId = (String) paramMap.get("enpId");
		String buildingAreaId = (String) paramMap.get("buildingAreaId");
		
		BuildingArea buildingArea = buildingDao.queryBaById(buildingAreaId,enpId);
		if(null!=buildingArea){
			paramMap.put("buildingId", buildingArea.getBuildingId());
			paramMap.put("buildingFloor", buildingArea.getFloor());
			paramMap.put("buildingArea", buildingArea.getAreaName());
		}else{
			throw new BusinessException("区域ID不存在！", "403");
		}
		
		paramMap.remove("objectId");
		paramMap.remove("createUser");
		paramMap.remove("createTime");
		paramMap.remove("modifyTime");
		//paramMap.remove("active");
		paramMap.remove("enpId");
		
		Long manufactureTime1 = (Long)paramMap.get("manufactureTime");
		if(manufactureTime1!=null){
			Date manufactureTime = new Date(manufactureTime1);
			paramMap.put("manufactureTime", manufactureTime);
		}else{
			paramMap.remove("manufactureTime");
		}
		
		if (paramMap.size() == 0) {
			throw new BusinessException("要更新的数据不存在！", "403");
		}
		Equipment equ = equipmentDao.queryEquById(objectId,enpId);
		if (null == equ) {
			throw new BusinessException("设备设施数据不存在！", "403");
		}
		List<String> columnNames = new ArrayList<String>();
		List<Object> columnValues = new ArrayList<Object>();
		for (String key : paramMap.keySet()) {
			columnNames.add(TransformUtil.humpToLine2(key)); // 将驼峰命名转换为下划线
			columnValues.add(paramMap.get(key));
		}
		columnNames.add("modify_time");
		columnValues.add(new Date());
		this.update(columnNames.toArray(new String[columnNames.size()]), columnValues.toArray(),
				new String[] { "object_id" }, new Object[] { objectId }, null,"equipment");
		return objectId;
	}
	
	public boolean update(String[] columnNames, Object[] columnValues, String[] whereNames, Object[] whereValues,
			String whereFilter,String tableName) throws BusinessException, Exception {
		boolean updateResult = commonService.updateColumns(tableName, columnNames, columnValues, whereNames, whereValues,
				whereFilter);
		return updateResult;
	}
	/**
	  * 
	  * <p>功能描述:更新维护计划状态。</p>	
	  * @param paramMap
	  * @return
	  * @throws BusinessException
	  * @throws Exception
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年5月14日 下午9:24:45。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object updateMaintenancePlan(Map<String, Object> paramMap) throws BusinessException, Exception {
		//获取objectId
		String objectId = (String) paramMap.get("objectId");
		//String enpId = (String) paramMap.get("enpId");
		Integer fault = (Integer) paramMap.get("fault");
		
		if (!StringUtil.hasText(fault.toString())) {
			throw new BusinessException("维护状态不能为空！", "403");
		}
		paramMap.remove("objectId");
		paramMap.remove("createUser");
		paramMap.remove("createTime");
		paramMap.remove("modifyTime");
		paramMap.remove("active");
		//paramMap.remove("enpId");
		
		List<String> columnNames = new ArrayList<String>();
		List<Object> columnValues = new ArrayList<Object>();
		for (String key : paramMap.keySet()) {
			columnNames.add(TransformUtil.humpToLine2(key)); // 将驼峰命名转换为下划线
			columnValues.add(paramMap.get(key));
		}
		columnNames.add("modify_time");
		columnValues.add(new Date());
		
		columnNames.add("status");
		columnValues.add(1);
		columnNames.add("actual_time");
		columnValues.add(new Date());
		this.update(columnNames.toArray(new String[columnNames.size()]), columnValues.toArray(),
				new String[] { "object_id" }, new Object[] { objectId }, null,"maintenace_plan");
		return objectId;
	}
	
	@Override
	public Object queryMaintenanceUser(Map<String, Object> paramMap) throws BusinessException, Exception {
		return equipmentDao.queryMaintenanceUser(paramMap);
	}
}

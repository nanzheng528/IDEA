package com.freedoonline.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.freedoonline.common.util.TransformUtil;
import com.freedoonline.domain.EnergyDao;
import com.freedoonline.domain.entity.Electricity;
import com.freedoonline.service.EnergyService;
import com.freedoonline.service.ICommonService;
import com.freedoonline.service.bo.EnergyBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

@Service("energyServiceImpl")
public class EnergyServiceImpl implements EnergyService {

	@Resource(name = "energyDaoImpl")
	private EnergyDao energyDao;
	
	@Resource(name = "commonServiceImpl")
	private ICommonService commonService;

	@SuppressWarnings("unchecked")
	@Override
	public Object waterConsumption(Map<String, Object> param) throws BusinessException, Exception {
//		List<String> month = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
//		List<String> quarter = Lists.newArrayList("1", "2", "3", "4");
		String buildingId = (String) param.get("buildingId");
		String timeSlot = (String) param.get("timeSlot");
		Integer eneType = (Integer) param.get("eneType");
		String startTime = (String)param.get("startTime");
		String enpId = (String)param.get("enpId");
		
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		if (!StringUtil.hasText(timeSlot)) {
			throw new BusinessException("时间标识不能为空！", "403");
		}
		if (!StringUtil.hasText(eneType.toString())) {
			throw new BusinessException("数据标识不能为空！", "403");
		}
		if (!StringUtil.hasText(startTime)) {
			throw new BusinessException("开始时间不能为空！", "403");
		}
		if (!StringUtil.hasText(enpId)) {
			throw new BusinessException("结束时间不能为空！", "403");
		}
		List<EnergyBo> list = (List<EnergyBo>) energyDao.waterConsumption(param);
		Comparator<EnergyBo> comparator = new Comparator<EnergyBo>() {
			public int compare(EnergyBo s1, EnergyBo s2) {
				if (s1.getDate() != s2.getDate()) {
					return s1.getDate().compareTo(s2.getDate());
				} 
				return -1;
			}
		};
		Collections.sort(list,comparator);
		
		Map<String, Object> result  = new HashMap<String, Object>();
		
//		Map<String, String> value= new HashMap<String, String>();
		List<String> date = new ArrayList<>();
		List<Double> value = new ArrayList<>();
		
		if(list!=null & list.size()>0){
			for(int i=0;i<list.size();i++){
				EnergyBo e = list.get(i);
				date.add(e.getDate());
				value.add(Double.parseDouble(e.getValue()));
			}
		}
		if(timeSlot.equals("2")){
			result.put("limit",   20000);
			result.put("optimum", 14000);
		}else if(timeSlot.equals("3")){
			result.put("limit",   60000);
			result.put("optimum", 42000);
		}else if(timeSlot.equals("4")){
			result.put("limit",   240000);
			result.put("optimum", 168000);
		}
		result.put("consumeNum", value);
		result.put("date", date);

//		// 接收数据年对应的月份
//		Map<String, List<String>> mmap = new HashMap<String, List<String>>();
//		// 接收年
//		List<String> ylist = new ArrayList<>();
//		// 接收标识
//		String sign = "";
//
//		if (null != list || list.size() > 0) {
//
//			for (int i = 0; i < list.size(); i++) {
//				EnergyBo energyBo = list.get(i);
//				sign = energyBo.getSign();
//				if (sign.indexOf("2") != -1) {
//					String y = energyBo.getDate().split("-")[0];
//					String m = energyBo.getDate().split("-")[1];
//					List<String> mlist = mmap.get(y);
//					if(mlist==null){
//						mlist = new ArrayList<String>();
//					}
//					mlist.add(m);
//					ylist.add(y);
//					mmap.put(y, mlist);
//				} else if (sign.equals("3")) {
//					String y = energyBo.getDate().split("_")[0];// 年
//					String m = energyBo.getDate().split("_")[1];// 季度
//					List<String> mlist = mmap.get(y);
//					if(mlist==null){
//						mlist = new ArrayList<String>();
//					}
//					mlist.add(m);
//					ylist.add(y);
//					mmap.put(y, mlist);
//				} else if (sign.equals("4")) {
//					ylist.add(energyBo.getDate());
//					Collections.sort(ylist);
//				}
//			}
//
//			for (int j = 0; j < ylist.size(); j++) {
//					if (sign.indexOf("2") != -1) {
//						List<String> guava = getDifferenceSetByGuava(month, mmap.get(ylist.get(j)));
//						for (String s : guava) {
//							EnergyBo e = new EnergyBo();
//							e.setDate(ylist.get(j) + "-" + s);
//							e.setSign("2");
//							e.setValue("0");
//							list.add(e);
//						}
//					} else if (sign.equals("3")) {
//						List<String> guava = getDifferenceSetByGuava(quarter, mmap.get(ylist.get(j)));
//						for (String s : guava) {
//							EnergyBo e = new EnergyBo();
//							e.setDate(ylist.get(j) + "_" + s);
//							e.setSign("3");
//							e.setValue("0");
//							list.add(e);
//						}
//					} else if (sign.equals("4")) {
//						for (int i = Integer.parseInt(ylist.get(0)); i < (Integer
//								.parseInt(ylist.get(ylist.size() - 1))); i++) {
//							if (!ylist.contains(i + "")) {
//								EnergyBo e = new EnergyBo();
//								e.setDate(i + "");
//								e.setValue("0");
//								e.setSign("4");
//								list.add(e);
//							}
//						}
//						return list;
//					}
//				
//			}
//		}
		return result;
	}

	private static List<String> getDifferenceSetByGuava(List<String> big, List<String> small) {
		Set<String> differenceSet = Sets.difference(Sets.newHashSet(big), Sets.newHashSet(small));
		return Lists.newArrayList(differenceSet);
	}
	
	@Override
	public Object eleAdd(Electricity electricity) throws BusinessException, Exception {
		if (!StringUtil.hasText(electricity.getMultimeterId())) {
			throw new BusinessException("所属电表不能为空！", "403");
		}
		if (!StringUtil.hasText(electricity.getBillTime())) {
			throw new BusinessException("账单日期不能为空！", "403");
		}
		if (!StringUtil.hasText(electricity.getTotalPower())) {
			throw new BusinessException("用电总量不能为空！", "403");
		}
		return energyDao.eleAdd(electricity);
	}
	
	@Override
	public String eleUpdate(Map<String, Object> paramMap) throws BusinessException, Exception {
		String objectId = (String) paramMap.get("objectId");
		String enpId = (String) paramMap.get("enpId");
		paramMap.remove("objectId");
		paramMap.remove("createUser");
		paramMap.remove("createTime");
		paramMap.remove("modifyTime");
		paramMap.remove("active");
		paramMap.remove("enpId");
		if (paramMap.size() == 0) {
			return objectId;
		}
		Electricity ele = (Electricity) energyDao.queryEleById(objectId,enpId);
		
		if (null == ele) {
			throw new BusinessException("用电数据数据不存在！", "403");
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
				new String[] { "object_id" }, new Object[] { objectId }, null,"electricity");
		return objectId;
	}
	
	public boolean update(String[] columnNames, Object[] columnValues, String[] whereNames, Object[] whereValues,
			String whereFilter,String tableName) throws BusinessException, Exception {
		boolean updateResult = commonService.updateColumns(tableName, columnNames, columnValues, whereNames, whereValues,
				whereFilter);
		return updateResult;
	}
	
	@Override
	public Object eleQuery(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return energyDao.eleQuery(param);
	}
}

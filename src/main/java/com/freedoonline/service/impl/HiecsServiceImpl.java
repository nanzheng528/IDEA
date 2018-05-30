package com.freedoonline.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.freedoonline.common.util.TransformUtil;
import com.freedoonline.domain.HiecsDao;
import com.freedoonline.domain.entity.Hiecs;
import com.freedoonline.service.HiecsService;
import com.freedoonline.service.ICommonService;
import com.freedoonline.service.bo.HiecsBo;
import com.freedoonline.service.bo.HiecsStandard;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.exception.BusinessException;
import cn.cloudlink.core.common.utils.StringUtil;

/**
 * 
 * <p>
 * 类描述：室内安全实现业务层实现。
 * </p>
 * 
 * @author 刘建雨。
 * @version v1.0。
 * @since JDK1.8。
 *        <p>
 * 		创建日期：2018年4月29日 下午1:49:10。
 *        </p>
 */
@Service("hiecsServiceImpl")
public class HiecsServiceImpl implements HiecsService {

	@Resource(name = "hiecsDaoImpl")
	private HiecsDao hiecsDao;

	@Resource(name = "commonServiceImpl")
	private ICommonService commonService;

	@Value("${hiecs.temperatureSummer}")
	private double temperatureSummer;

	@Value("${hiecs.temperatureWinter}")
	private double temperatureWinter;

	@Value("${hiecs.humiditySummer}")
	private double humiditySummer;

	@Value("${hiecs.humidityWinter}")
	private double humidityWinter;

	@Value("${hiecs.CO2}")
	private double CO2;

	@Value("${hiecs.PM}")
	private double PM;
	
	@Value("${hiecs.HCHO}")
	private double HCHO;

	/**
	 * 
	 * <p>
	 * 功能描述:室内数据添加。
	 * </p>
	 * 
	 * @param hiecs
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 *             <p>
	 *             刘建雨
	 *             </p>
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期2018年4月29日 下午2:13:27。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@Override
	public Object addHiecs(Hiecs hiecs) throws BusinessException, Exception {
		if (!StringUtil.hasText(hiecs.getBuildingId())) {
			throw new BusinessException("所属楼宇不能为空！", "403");
		}
		if (!StringUtil.hasText(hiecs.getFloor())) {
			throw new BusinessException("所属楼层不能为空！", "403");
		}
		if (!StringUtil.hasText(hiecs.getTemperature())) {
			throw new BusinessException("室内温度不能为空！", "403");
		}
		if (!StringUtil.hasText(hiecs.getHumidity())) {
			throw new BusinessException("室内湿度不能为空！", "403");
		}
		if (!StringUtil.hasText(hiecs.getCo2())) {
			throw new BusinessException("室内CO2值不能为空！", "403");
		}
		if (!StringUtil.hasText(hiecs.getPm())) {
			throw new BusinessException("室内PM值不能为空！", "403");
		}
		if (!StringUtil.hasText(hiecs.getHcho())) {
			throw new BusinessException("室内HCHO值不能为空！", "403");
		}
		double score = 0;
		if (Double.parseDouble(hiecs.getPm()) < PM) {
			score += 10;
		}
		if (Double.parseDouble(hiecs.getCo2()) < CO2) {
			score += 20;
		}
		if (Double.parseDouble(hiecs.getHcho()) < HCHO) {
			score += 10;
		}
		// 获取当前季节

		if ((Double.parseDouble(hiecs.getTemperature()) >= (temperatureSummer - 1)
				&& Double.parseDouble(hiecs.getTemperature()) <= (temperatureSummer + 1))
				&& (Double.parseDouble(hiecs.getHumidity()) >= (humiditySummer - 10)
						&& Double.parseDouble(hiecs.getHumidity()) <= (humiditySummer + 10))) {
			score += 60;
		}
		hiecs.setScore(score);
		return hiecsDao.addHiecs(hiecs);
	}

	/**
	 * 
	 * <p>
	 * 功能描述:查看室内数据详情。
	 * </p>
	 * 
	 * @param objectId
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 *             <p>
	 *             刘建雨
	 *             </p>
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期2018年4月29日 下午7:57:46。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@Override
	public Hiecs queryHiecsById(String objectId) throws BusinessException, Exception {
		return hiecsDao.queryHiecsById(objectId);
	}

	/**
	 * 
	 * <p>
	 * 功能描述:批量删除室内健康数据。
	 * </p>
	 * 
	 * @param objectId
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 *             <p>
	 *             刘建雨
	 *             </p>
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期2018年4月29日 下午8:21:04。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void delHiecsById(Map<String, Object> param) throws BusinessException, Exception {
		List<String> idList = (List) param.get("idList");
		if (idList != null && idList.size() > 0) {
			for (String objectId : idList) {
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("objectId", objectId);
				resultMap.put("active", 0);
				List<String> columnNames = new ArrayList<String>();
				List<Object> columnValues = new ArrayList<Object>();
				for (String key : resultMap.keySet()) {
					columnNames.add(TransformUtil.humpToLine2(key)); // 将驼峰命名转换为下划线
					columnValues.add(resultMap.get(key));
				}
				columnNames.add("modify_time");
				columnValues.add(new Date());
				this.updateHiecs(columnNames.toArray(new String[columnNames.size()]), columnValues.toArray(),
						new String[] { "object_id" }, new Object[] { objectId }, null);
			}
		}
	}

	/**
	 * 
	 * <p>
	 * 功能描述：更新通用方法。
	 * </p>
	 * <p>
	 * 刘建雨。
	 * </p>
	 * 
	 * @param columnNames
	 * @param columnValues
	 * @param whereNames
	 * @param whereValues
	 * @param whereFilter
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期:2018年4月29日 下午8:50:59。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	public boolean updateHiecs(String[] columnNames, Object[] columnValues, String[] whereNames, Object[] whereValues,
			String whereFilter) throws BusinessException, Exception {
		boolean updateResult = commonService.updateColumns("hiecs", columnNames, columnValues, whereNames, whereValues,
				whereFilter);
		return updateResult;
	}

	/**
	 * 
	 * <p>
	 * 功能描述:更新室内健康数据。
	 * </p>
	 * 
	 * @param objectId
	 * @return
	 * @throws BusinessException
	 * @throws Exception
	 *             <p>
	 *             刘建雨
	 *             </p>
	 * @since JDK1.8。
	 *        <p>
	 * 		创建日期2018年4月29日 下午9:06:57。
	 *        </p>
	 *        <p>
	 * 		更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。
	 *        </p>
	 */
	@Override
	public String updateHiecsById(Map<String, Object> paramMap) throws BusinessException, Exception {
		String objectId = (String) paramMap.get("objectId");
//		String temperature = (String) paramMap.get("temperature");
//		String humidity = (String) paramMap.get("humidity");
//		String co2 = (String) paramMap.get("co2");
//		String pm = (String) paramMap.get("pm");

		paramMap.remove("objectId");
		paramMap.remove("createUser");
		paramMap.remove("createTime");
		paramMap.remove("modifyTime");
		paramMap.remove("active");
		paramMap.remove("score");
		if (paramMap.size() == 0) {
			return objectId;
		}
		Hiecs hiecs = (Hiecs) hiecsDao.queryHiecsById(objectId);
		if (null == hiecs) {
			throw new BusinessException("室内健康数据不存在！", "403");
		}
//		// 打分
//		double score = 0;
//		if (Double.parseDouble(pm) < PM) {
//			score += 20;
//		}
//		if (Double.parseDouble(co2) < CO2) {
//			score += 20;
//		}
//		// 获取当前季节
//		if ((Double.parseDouble(temperature) >= (temperatureSummer - 1)
//				&& Double.parseDouble(temperature) <= (temperatureSummer + 1))
//				&& (Double.parseDouble(humidity) >= (humiditySummer - 10)
//						&& Double.parseDouble(humidity) <= (humiditySummer + 10))) {
//			score += 60;
//		}
//		hiecs.setScore(score);

		List<String> columnNames = new ArrayList<String>();
		List<Object> columnValues = new ArrayList<Object>();
		for (String key : paramMap.keySet()) {
			columnNames.add(TransformUtil.humpToLine2(key)); // 将驼峰命名转换为下划线
			columnValues.add(paramMap.get(key));
		}
		columnNames.add("modify_time");
		columnValues.add(new Date());
		this.updateHiecs(columnNames.toArray(new String[columnNames.size()]), columnValues.toArray(),
				new String[] { "object_id" }, new Object[] { objectId }, null);
		return null;
	}

	@Override
	public List<Hiecs> queryListHiecs(HiecsBo queryBo) throws BusinessException, Exception {
		return hiecsDao.queryListHiecs(queryBo);
	}

	@Override
	public Page<Hiecs> queryListHiecs(PageRequest pageRequest, HiecsBo queryBo) throws BusinessException, Exception {
		return hiecsDao.queryListHiecs(pageRequest, queryBo);
	}

	@Override
	public Map<String, Object> colligation(HiecsBo queryBo) throws BusinessException, Exception {
		int co2 = 0;
		int pm = 0;
		int temperature = 0;
		int humidity = 0;
		int th = 0;
		int hcho = 0;
		Map<String, Object> resultMap = new HashMap<>();
		if (!StringUtil.hasText(queryBo.getBuildingId())) {
			throw new BusinessException("所属楼宇不能为空！", "403");
		}
		List<Hiecs> list = hiecsDao.queryListHiecs(queryBo);
		if (StringUtil.hasText(queryBo.getArea()) && StringUtil.hasText(queryBo.getFloor())) {
			if (list.size() == 1) {
				Hiecs hiecs = list.get(0);
				if ((Double.parseDouble(hiecs.getTemperature()) >= (temperatureSummer - 1)
						&& Double.parseDouble(hiecs.getTemperature()) <= (temperatureSummer + 1))
						&& (Double.parseDouble(hiecs.getHumidity()) >= (humiditySummer - 10)
								&& Double.parseDouble(hiecs.getHumidity()) <= (humiditySummer + 10))) {
					resultMap.put("temperature", 100);
					resultMap.put("humidity", 100);
				} else {
					resultMap.put("temperature", 0);
					resultMap.put("humidity", 0);
				}
				if (Double.parseDouble(hiecs.getPm()) < PM) {
					resultMap.put("pm", 100);
				} else {
					resultMap.put("pm", 0);
				}
				if (Double.parseDouble(hiecs.getCo2()) < CO2) {
					resultMap.put("co2", 100);
				} else {
					resultMap.put("co2", 0);
				}
				
				if (Double.parseDouble(hiecs.getHcho()) < HCHO) {
					resultMap.put("hcho", 100);
				} else {
					resultMap.put("hcho", 0);
				}

			}
		} else {
			int sum = list.size();
			if (list != null && list.size() > 0) {
				for (Hiecs hiecs : list) {
					if (Double.parseDouble(hiecs.getCo2()) < CO2) {
						co2++;
					}
					if (Double.parseDouble(hiecs.getPm()) < PM) {
						pm++;
					}
					if (Double.parseDouble(hiecs.getHcho()) < HCHO) {
						hcho++;
					}
					if ( (Double.parseDouble(hiecs.getTemperature()) >= (temperatureSummer - 1)
							&& Double.parseDouble(hiecs.getTemperature()) <= (temperatureSummer + 1))  ) {
						temperature++;
					}
					if ((Double.parseDouble(hiecs.getHumidity()) >= (humiditySummer - 10)
							&& Double.parseDouble(hiecs.getHumidity()) <= (humiditySummer + 10))) {
						humidity++;
					}
					if ((Double.parseDouble(hiecs.getTemperature()) >= (temperatureSummer - 1)
							&& Double.parseDouble(hiecs.getTemperature()) <= (temperatureSummer + 1))
							&& (Double.parseDouble(hiecs.getHumidity()) >= (humiditySummer - 10)
									&& Double.parseDouble(hiecs.getHumidity()) <= (humiditySummer + 10))){
						th++;
					}

				}
				resultMap.put("co2", new HiecsStandard(((100 * co2) / sum),CO2+"",CO2+""));
				resultMap.put("pm", new HiecsStandard(((100 * pm) / sum),PM+"",PM+""));
				//resultMap.put("th", new HiecsStandard(((100 * th) / sum),(temperatureSummer+1)+","+(humiditySummer+10)+"",""+(temperatureSummer-1)+","+(humiditySummer-10)+""));
				Map<String, Object> thResult = new HashMap<>();
				thResult.put("score", ((100 * temperature) / sum));
				thResult.put("maxStandardTem", (temperatureSummer+1)+"");
				thResult.put("maxStandardHum", (humiditySummer+10)+"");
				thResult.put("minStandardTem", (temperatureSummer-1)+"");
				thResult.put("minStandardHum", (humiditySummer-10)+"");
				resultMap.put("th",thResult);
				resultMap.put("temperature", new HiecsStandard(((100 * temperature) / sum),(temperatureSummer+1)+"",(temperatureSummer-1)+""));
				resultMap.put("humidity",    new HiecsStandard(((100 * humidity) / sum),(humiditySummer+10)+"",(humiditySummer-10)+""));
				resultMap.put("hcho", new HiecsStandard(((100 * hcho) / sum),HCHO+"",HCHO+""));
				resultMap.put("totalScore", ((((100 * co2) / sum))+(((100 * pm) / sum))+((100 * th) / sum)+((100 * hcho) / sum))/4);
			}
		}
		// if(StringUtil.hasText(queryBo.getFloor())){
		// int sum = list.size();
		// if(list!=null && list.size()>0){
		// for(Hiecs hiecs:list){
		// if(Double.parseDouble(hiecs.getCo2())<CO2){
		// co2++;
		// }
		// if(Double.parseDouble(hiecs.getPm())<PM){
		// pm++;
		// }
		// if((Double.parseDouble(hiecs.getTemperature()) >=
		// (temperatureSummer-1) && Double.parseDouble(hiecs.getTemperature())
		// <= (temperatureSummer+1))){
		// temperature++;
		// }
		// if( (Double.parseDouble(hiecs.getHumidity()) >= (humiditySummer-10)
		// && Double.parseDouble(hiecs.getHumidity()) <= (humiditySummer+10))){
		// humidity++;
		// }
		//
		// }
		// resultMap.put("Co2", (100*co2)/sum);
		// resultMap.put("pm", (100*pm)/sum);
		// resultMap.put("temperature", (100*temperature)/sum);
		// resultMap.put("humidity", (100*humidity)/sum);
		// }
		// }
		return resultMap;
	}
	
	@Override
	public Object pushMsg(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return hiecsDao.pushMsg(param);
	}
	
	@Override
	public Object queryType(Map<String, Object> param) throws BusinessException, Exception {
		String buildingId = (String)param.get("buildingId");
		if (!StringUtil.hasText(buildingId)) {
			throw new BusinessException("楼宇ID不能为空！", "403");
		}
		return hiecsDao.queryType(param);
	}
}

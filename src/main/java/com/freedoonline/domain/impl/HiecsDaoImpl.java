package com.freedoonline.domain.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.freedoonline.domain.HiecsDao;
import com.freedoonline.domain.entity.Hiecs;
import com.freedoonline.service.bo.HiecsBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.utils.StringUtil;

/**
  * 
  *<p>类描述：室内健康数据层实现。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月29日 下午2:25:35。</p>
  */
@Repository
public class HiecsDaoImpl implements HiecsDao {
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
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
	
	static String INSERT_HIECS_SQL = "";
	static String SELECT_HIECS_SQL = "";
	static String QUERY_HIECS_SQL = "";
	
	static{
		INSERT_HIECS_SQL = " INSERT INTO HIECS (object_id,  building_id,  floor,  area,  temperature,  humidity,  CO2,  PM,  hcho ,create_user,  create_time,  modify_user,  modify_time,  remark, score ,active) VALUES (?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ? ,?); ";
		SELECT_HIECS_SQL = " SELECT object_id,  building_id,  floor,  area,  temperature,  humidity,  CO2,  PM,  hcho,create_user,  create_time,  modify_user,  modify_time,  remark, score FROM HIECS ";
		QUERY_HIECS_SQL = " select * from hiecs where  active =1 and object_id in (select object_id from hiecs where modify_time in (select max(modify_time) from hiecs where active =1 group by floor,area)) ";
	}
	
	/**
	  * 
	  * <p>功能描述:室内数据添加。</p>	
	  * @param hiecs
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年4月29日 下午2:25:11。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public String addHiecs(Hiecs hiecs) {
		String objectId = StringUtil.hasText(hiecs.getObjectId())?hiecs.getObjectId():UUID.randomUUID().toString();
		Object[] args =  {
			objectId, hiecs.getBuildingId(), hiecs.getFloor(), hiecs.getArea()!=null?hiecs.getArea():1, 
			hiecs.getTemperature(), hiecs.getHumidity(), hiecs.getCo2(), hiecs.getPm(), hiecs.getHcho(),
			hiecs.getCreateUser(), hiecs.getCreateTime()!=null?hiecs.getCreateTime():new Date(), 
			hiecs.getModifyUser(), hiecs.getModifyTime()!=null?hiecs.getModifyTime():new Date(), 
			hiecs.getRemark(), hiecs.getScore(),1
		};
		baseJdbcDao.save(INSERT_HIECS_SQL,  args);
		return objectId;
	}
	
	/**
	  * 
	  * <p>功能描述:根据ID查询数据实例。</p>	
	  * @param objectId
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年4月29日 下午7:54:11。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Hiecs queryHiecsById(String objectId) {
		StringBuffer buffer = new StringBuffer(SELECT_HIECS_SQL);
		buffer.append(" where object_id = ? AND active = 1 ");
		Object[] args = {objectId};
		return (Hiecs)baseJdbcDao.queryForObject(buffer.toString(), args, Hiecs.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Hiecs> queryListHiecs(HiecsBo queryBo) {
		StringBuffer buffer = new StringBuffer(QUERY_HIECS_SQL);
		Map<String,Object> map = getEnpQueryCondition(queryBo,1);
		Object[] args = (Object[])map.get("args");
		buffer.append(map.get("where"));  //拼接条件语句
		List<Hiecs> floorList = baseJdbcDao.queryForList(buffer.toString(), args, Hiecs.class);
		return floorList;
	}
	
	@Override
	public Page<Hiecs> queryListHiecs(PageRequest pageRequest, HiecsBo queryBo) {
		Page<Hiecs> resultPage = new Page<>(pageRequest);
		resultPage.setResult(this.queryListHiecs(queryBo));
		return resultPage;
	}
	
	
	/**
	  * 
	  * <p>功能描述：拼接查询条件。</p>
	  * <p> 刘建雨。</p>	
	  * @param queryBo
	  * @param queryRange
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2018年5月1日 下午3:32:33。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	private Map<String,Object> getEnpQueryCondition(HiecsBo queryBo,Integer queryRange){
		
		StringBuffer whereBuffer = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		
		if(StringUtil.hasText(queryBo.getObjectId())){
			whereBuffer.append(" and object_id like  ?  ");
			args.add("%"+queryBo.getObjectId()+"%");
		}
		if(StringUtil.hasText(queryBo.getBuildingId())){
			whereBuffer.append(" and building_id like  ?  ");
			args.add("%"+queryBo.getBuildingId()+"%");
		}
	
		if(StringUtil.hasText(queryBo.getFloor())){
			whereBuffer.append(" and floor like  ?  ");
			args.add("%"+queryBo.getFloor()+"%");
		}
		if(StringUtil.hasText(queryBo.getArea())){
			whereBuffer.append(" and area like  ?  ");
			args.add("%"+queryBo.getArea()+"%");
		}
		if(StringUtil.hasText(queryBo.getTemperature())){
			whereBuffer.append(" and temperature like  ?  ");
			args.add("%"+queryBo.getTemperature()+"%");
		}
		if(StringUtil.hasText(queryBo.getHumidity())){
			whereBuffer.append(" and humidity like  ?  ");
			args.add("%"+queryBo.getHumidity()+"%");
		}
		if(StringUtil.hasText(queryBo.getCo2())){
			whereBuffer.append(" and co2 like  ?  ");
			args.add("%"+queryBo.getCo2()+"%");
		}
		if(StringUtil.hasText(queryBo.getPm())){
			whereBuffer.append(" and pm like  ?  ");
			args.add("%"+queryBo.getPm()+"%");
		}
		if(StringUtil.hasText(queryBo.getHcho())){
			whereBuffer.append(" and hcho like  ?  ");
			args.add("%"+queryBo.getHcho()+"%");
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("where", whereBuffer.toString());
		resultMap.put("args", args.toArray());
		return resultMap;
	}
	
	@Override
	public Object pushMsg(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		
		StringBuffer buffer = new StringBuffer(" SELECT h.* ,5 as sign FROM hiecs h LEFT JOIN building b ON  h.building_id = b.object_id AND b.active=1 WHERE h.object_id IN (SELECT object_id FROM hiecs WHERE modify_time IN (SELECT max(modify_time) FROM hiecs WHERE active = 1 GROUP BY floor,area)) AND b.enp_id =? AND b.object_id = ?  AND ( (h.temperature not BETWEEN  ? and ? ) or  (h.humidity  not BETWEEN  ? and ? ) or  h.pm > ? OR h.co2 > ? or h.hcho> ?) ");
		Object[] args ={enpId,buildingId,temperatureSummer-1,temperatureSummer+1,humiditySummer-10,humiditySummer+10,PM,CO2,HCHO};
		return baseJdbcDao.queryForList(buffer.toString(), args, Hiecs.class);
	}
	
	@Override
	public Object queryType(Map<String, Object> param) {
		String buildingId = (String)param.get("buildingId");
		String enpId = (String)param.get("enpId");
		String type = (String)param.get("type");
		StringBuffer buffer = new StringBuffer(" SELECT h.* ,5 as sign FROM hiecs h LEFT JOIN building b ON  h.building_id = b.object_id AND b.active=1 WHERE h.object_id IN (SELECT object_id FROM hiecs WHERE modify_time IN (SELECT max(modify_time) FROM hiecs WHERE active = 1 GROUP BY floor,area)) AND b.enp_id =? AND b.object_id = ? ");
		if(StringUtil.hasText(type)){
			if(type.equals("th")){
				buffer.append("  AND  ((h.temperature not BETWEEN  ? and ? ) or (h.humidity  not BETWEEN  ? and ? )) ");
				Object[] args ={enpId,buildingId,temperatureSummer-1,temperatureSummer+1,humiditySummer-10,humiditySummer+10};
				return baseJdbcDao.queryForList(buffer.toString(), args, Hiecs.class);
//			}//else if (type.equals("humidity")){
//				buffer.append(" AND (h.humidity  not BETWEEN  ? and ? ) ");
//				Object[] args ={enpId,buildingId,humiditySummer-10,humiditySummer+10};
//				return baseJdbcDao.queryForList(buffer.toString(), args, Hiecs.class);
			}else if(type.equals("pm")){
				buffer.append(" AND h.pm > ?  ");
				Object[] args ={enpId,buildingId,PM};
				return baseJdbcDao.queryForList(buffer.toString(), args, Hiecs.class);
			}else if(type.equals("co2")){
				buffer.append(" AND h.co2 > ? ");
				Object[] args ={enpId,buildingId,CO2};
				return baseJdbcDao.queryForList(buffer.toString(), args, Hiecs.class);
			}else if(type.equals("hcho")){
				buffer.append(" AND h.hcho > ? ");
				Object[] args ={enpId,buildingId,HCHO};
				return baseJdbcDao.queryForList(buffer.toString(), args, Hiecs.class);
			}
		}
		return null;
	}
}

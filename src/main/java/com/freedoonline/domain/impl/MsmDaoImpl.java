package com.freedoonline.domain.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.freedoonline.common.util.CommonUtil;
import com.freedoonline.domain.IMsmDao;
import com.freedoonline.service.bo.MsgTemplateQueryBo;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.utils.StringUtil;
import cn.cloudlink.core.framework.domain.entity.MsgTemplate;

/**
 *<p>类描述：短信服务SMS数据操作接口实现类。</p>
 * @version v1.0.0.1。
 * @since JDK1.8。
 *<p>创建日期：2017年4月15日 下午5:46:04。</p>
*/
@Repository
public class MsmDaoImpl implements IMsmDao{

	@Resource
	private BaseJdbcDao baseJdbcDao;
	
	static String QUERY_SQL = " select t.object_id,t.template_type,t.use_category,t.scene_type,t.template_num,t.template_param,t.description, "
			+ " t.status,t.template_content,t.create_time,t.create_user,t.modify_time,t.modify_user,t.active "
			+ " from msg_template t  ";
	


	
	/**
	  * <p>功能描述：根据条件查询模板。</p>
	  * @param queryBo：
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2017年4月15日 下午2:41:20。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	 */
	@Override
	public List<MsgTemplate> queryListMsgTemplate(MsgTemplateQueryBo queryBo){
		StringBuffer sql=new StringBuffer(QUERY_SQL);
		sql.append(" where 1=1 ");
		Map<String,Object> map = getQueryCondition(queryBo);	
		sql.append(map.get("where"));  //拼接条件语句
		Object[] args = (Object[])map.get("args");
		List<MsgTemplate> resultList =baseJdbcDao.queryForList(sql.toString(), args, MsgTemplate.class); 
		return  resultList;
		
		
	}
	 /**
	  * <p>功能描述：封装查询条件</p>
	  * @param queryBo 查询条件bo对象
	  * @return
	  * @since JDK1.8。
	  * <p>创建日期:2016年10月25日 下午2:20:35。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][申杰][变更描述]。</p>
	 */
	private Map<String,Object> getQueryCondition(MsgTemplateQueryBo queryBo){
		//拼接查询条件
		StringBuffer whereBuffer = new StringBuffer();
		List<Object> args = new ArrayList<Object>();
		if(StringUtil.hasText(queryBo.getActive())){
			//有效标识
			String inSql = CommonUtil.getIdsFormat(queryBo.getActive());
			whereBuffer.append(" and t.active in ( ? ) ");
			args.add(inSql);
		}else{
			//默认为1
			whereBuffer.append(" and t.active=1 "); 
		}
		if(StringUtil.hasText(queryBo.getStatus())){
			//状态
			String inSql = CommonUtil.getIdsFormat(queryBo.getStatus());
			whereBuffer.append(" and t.status in ( ? ) ");
			args.add(inSql);
		}else{
			//默认为1
			whereBuffer.append(" and t.status=1 "); 
		}
		if(StringUtil.hasText(queryBo.getObjectId())){
			//企业ID
			whereBuffer.append(" and t.object_id=? ");
			args.add(queryBo.getObjectId());
		}
		if(StringUtil.hasText(queryBo.getTemplateType())){
			//模板类型,短信message 邮件 mail
			whereBuffer.append(" and t.template_type =? ");
			args.add(queryBo.getTemplateType());
		}
		if(StringUtil.hasText(queryBo.getUseCategory())){
			//使用类别 ,0通用 注册为"1",验证码登录"2" 邀请用户"4"邀请注册时"5",企业认证审核通过"6",企业认证审核未通过"7",移交管理员"8"
			whereBuffer.append(" and t.use_category = ? ");
			args.add(queryBo.getUseCategory());
		}
		if(StringUtil.hasText(queryBo.getSceneType())){
			//场景类型:比如企业认证：成功1驳回-1
			whereBuffer.append(" and t.scene_type = ?  ");
			args.add(queryBo.getSceneType());
		}
		if(StringUtil.hasText(queryBo.getTemplateNum())){
			//模板编号
			whereBuffer.append(" and t.template_num= ? ");
			args.add(queryBo.getTemplateNum());
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("where", whereBuffer.toString());
		resultMap.put("args", args.toArray());
		return resultMap;
	}
}

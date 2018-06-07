package com.freedoonline.domain.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freedoonline.domain.UserDao;
import com.freedoonline.domain.entity.User;

import cn.cloudlink.core.common.dataaccess.BaseJdbcDao;
import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;
import cn.cloudlink.core.common.utils.CryptUtil;
import cn.cloudlink.core.common.utils.StringUtil;

/**
  * 
  *<p>类描述：用户数据层实现。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午9:17:11。</p>
  */
@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private BaseJdbcDao baseJdbcDao;
	
	static String SELECT_USER_SQL = "";
	static String INSERT_USER_SQL = "";
	static String SELECTSQL = "";
	static String SELECT_USER_BYROLE_SQL="";
	
	static {
		SELECT_USER_SQL = "select object_id, user_name, mobile_num, profile_photo, create_user, create_time, modify_user, modify_time, remark ,enp_id from user ";
		SELECTSQL = "select u.object_id, u.account, u.emp_id,u.department_id,u.position_id,u.user_name, u.status ,u.enp_id ,b.building_name ,e.enterprise_name from user  u left join building b on u.building_id = b.object_id left join enterprise e on e.object_id = u.enp_id  where u.active = '1'";
		INSERT_USER_SQL = " INSERT INTO user (object_id, account, emp_id,department_id,position_id,address,building_id,superior_id,enp_id,user_name, password, mobile_num,main_mobile_num,language,sex,birthday,emp_date,emp_end_date,approval_limits,id_card,job,is_outsourcing, email, status, profile_photo,role_id, create_user, create_time, modify_user, modify_time, active, remark) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
	}
	
	/**
	  * 
	  * <p>功能描述:根据用户名密码获取数据。</p>	
	  * @param loginNum
	  * @param password
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年4月27日 下午9:17:47。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object validateUser(String loginNum, String password) {
		StringBuffer buffer = new StringBuffer(SELECT_USER_SQL);
		buffer.append(" where mobile_num = ? and active = 1 and status =1 and  password= ?");
		Object[] args = {loginNum,password};
		User user = (User) baseJdbcDao.queryForObject(buffer.toString(), args, User.class);
		return user;
	}
	
	/**
	  * 
	  * <p>功能描述:获取用户详情。</p>	
	  * @param objectId
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年4月28日 上午11:11:13。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object validateUser(String objectId) {
		StringBuffer buffer = new StringBuffer(SELECT_USER_SQL);
		buffer.append(" where object_id = ? and active = 1 and status =1");
		Object[] args = {objectId};
		User user = (User) baseJdbcDao.queryForObject(buffer.toString(), args, User.class);
		return user;
	}
	
	/**
	  * 
	  * <p>功能描述:用户注册。</p>	
	  * @param user
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年4月28日 下午1:37:27。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public String addUser(User user) {
		String objectId = StringUtil.hasText(user.getObjectId())?user.getObjectId():UUID.randomUUID().toString();
		user.setActive(1);
		user.setStatus(1);
		user.setRoleId(2);
		try {
			//如果密码为空，则用随机生成的uuid的前8位作为密码
			if(!StringUtil.hasText(user.getPassword())){
				String[] randomPassword = UUID.randomUUID().toString().split("-");
				user.setPassword(CryptUtil.md5Encrypt(randomPassword[0]));
			} else{
				//密码不为空，设置为md5加密的密码
				user.setPassword(CryptUtil.md5Encrypt(user.getPassword()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setCreateTime(user.getCreateTime()!=null?user.getCreateTime():new Date());
		user.setModifyTime(user.getModifyTime()!=null?user.getModifyTime():new Date());
		user.setCreateUser(user.getCreateUser()!=null?user.getCreateUser():objectId);
		user.setModifyUser(user.getModifyUser()!=null?user.getModifyUser():objectId);
		Object[] args=  {objectId,user.getAccount(),user.getEmpId(),user.getDepartmentId(),user.getPositionId()
				,user.getAddress(),user.getBuildingId(),user.getSuperiorId(),user.getEnpId(),user.getUserName()
				,user.getPassword(),user.getMobileNum(),user.getMainMobileNum(),user.getLanguage(),user.getSex()
				,user.getBirthday(),user.getEmpDate(),user.getEmpEndDate(),user.getApprovalLimits(),user.getIdCard()
				,user.getJob(),user.getIsOutsourcing() == null ? 0 : user.getIsOutsourcing(),user.getEmail(),user.getStatus()
				,user.getProfilePhoto(),user.getRoleId(),user.getCreateUser(),user.getCreateTime(),user.getModifyUser()
				,user.getModifyTime(),user.getActive(),user.getRemark()};
		baseJdbcDao.save(INSERT_USER_SQL, args);
		return objectId;
	}
	
	/**
	  * 
	  * <p>功能描述:手机号验证。</p>	
	  * @param mobileNum
	  * @return
	  * <p> 刘建雨</p>
	  * @since JDK1.8。
	  * <p>创建日期2018年4月28日 下午7:47:36。</p>
	  * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	  */
	@Override
	public Object validateMobileNum(String mobileNum) {
		StringBuffer buffer = new StringBuffer(SELECT_USER_SQL);
		buffer.append(" where mobile_num = ? and active = 1 and status =1");
		Object[] args = {mobileNum};
		User user = (User) baseJdbcDao.queryForObject(buffer.toString(), args, User.class);
		return user;
	}

	@Override
	public Page queryUserList(PageRequest pageRequest, User user) {
		if(!StringUtil.hasText(pageRequest.getOrderBy())){
			pageRequest.setOrderBy("u.emp_id");
		}
		StringBuffer buffer = new StringBuffer(SELECTSQL);
		ArrayList<Object> args = new ArrayList<>();
		if(StringUtil.hasText(user.getObjectId())){
			buffer.append(" and u.object_id = ?");
			args.add(user.getObjectId());
		}
		if(StringUtil.hasText(user.getAccount())){
			buffer.append(" and u.account like ?");
			args.add("%"+user.getAccount()+"%");
		}
		if(StringUtil.hasText(user.getMobileNum())){
			buffer.append(" and u.mobile_num like ?");
			args.add("%"+user.getMobileNum()+"%");
		}
		if(StringUtil.hasText(user.getPositionId())){
			buffer.append(" and u.position_id = ?");
			args.add(user.getPositionId());
		}
		if(StringUtil.hasText(user.getDepartmentId())){
			buffer.append(" and u.department_id = ?");
			args.add(user.getDepartmentId());
		}
		if(StringUtil.hasText(user.getUserName())){
			buffer.append(" and u.user_name like ?");
			args.add("%"+user.getUserName()+"%");
		}
		if(StringUtil.hasText(user.getEmail())){
			buffer.append(" and u.email like ?");
			args.add("%"+user.getEmail()+"%");
		}
		if(StringUtil.hasText(user.getAddress())){
			buffer.append(" and u.address like ?");
			args.add("%"+user.getAddress()+"%");
		}
		if(StringUtil.hasText(user.getSuperiorId())){
			buffer.append(" and u.superior_id like ?");
			args.add("%"+user.getSuperiorId()+"%");
		}
		if(StringUtil.hasText(user.getEmpId())){
			buffer.append(" and u.emp_id like ?");
			args.add("%"+user.getEmpId()+"%");
		}
		if(StringUtil.hasText(user.getEnpId())){
			buffer.append(" and u.enp_id like ?");
			args.add("%"+user.getEnpId()+"%");
		}
		if(null == user.getRoleId()){
			buffer.append(" and u.role_id = ?");
			args.add(user.getRoleId());
		}
		return baseJdbcDao.queryPageMap(pageRequest, buffer.toString(), args.toArray());
	}
	
}

package com.freedoonline.domain;

import com.freedoonline.domain.entity.User;

import cn.cloudlink.core.common.dataaccess.data.Page;
import cn.cloudlink.core.common.dataaccess.data.PageRequest;

/**
  * 
  *<p>类描述：用户数据层接口。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午9:16:39。</p>
 */
public interface  UserDao {
	// 验证用户数据
	public Object validateUser(String loginNum, String password);
	// 用户详情
	public Object validateUser(String objectId);
	// 用户注册
	public User addUser(User user);
	// 验证手机号是否注册
	public Object validateMobileNum(String mobileNum);
	// 验证手机号是否注册
	public Page queryUserList(PageRequest pageRequest, User user);
}

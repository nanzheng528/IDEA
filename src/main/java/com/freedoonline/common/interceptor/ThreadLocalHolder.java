package com.freedoonline.common.interceptor;

import com.freedoonline.domain.entity.User;

/**
  * 
  *<p>类描述：ThreadLocalHolder工具类。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午8:56:58。</p>
  */
public class ThreadLocalHolder {

	private static ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();  
	
	private static ThreadLocal<String> tokenThreadLocal = new ThreadLocal<String>();  
	
	public static User getUser(){
		return userThreadLocal.get();
	}
	
	public static void setUser(User user){
		userThreadLocal.set(user);
	}

	public static void setToken(String token){
		tokenThreadLocal.set(token);
	}
	
	public static String getToken(){
		return tokenThreadLocal.get();
	}
}

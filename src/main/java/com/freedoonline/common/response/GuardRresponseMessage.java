package com.freedoonline.common.response;

/**
  * 
  *<p>类名：GuardRresponseMessage.java</p>
  * @Descprition 返回数据格式类
  * @author 南征
  * @version 1.0
  * @since JDK1.8
  *<p>创建日期：上午9:52:43</p>
  */
public class GuardRresponseMessage<T> {
	
	/**
	 * fieldType: int
	 * field: stat
	 * @Description 状态码  0:成功  -1:失败
	 */
	private int stat;
	/**
	 * fieldType: String
	 * field: msg
	 * @Description 消息 success:成功  error:失败
	 */
	private String msg;
	
	/**
	 * fieldType: T
	 * field: val
	 * @Description 响应数据
	 */
	private T val;
	
	private GuardRresponseMessage(int stat){
		this.stat = stat;
	}
	private GuardRresponseMessage(int stat, String msg, T val){
		this.stat = stat;
		this.msg = msg;
		this.val = val;
	}
	private GuardRresponseMessage(int stat, String msg){
		this.stat = stat;
		this.msg = msg;
	}
	private  GuardRresponseMessage(String msg,T val){
		this.msg = msg;
		this.val = val;
	}
	public int getStat(){
		return this.stat;
	}
	public String getMsg(){
		return this.msg;
	}
	public T getVal(){
		return this.val;
	}
	/** 
	* @Title: creatBySuccess 
	* @Description: 返回成功code
	* @return GuardRresponseMessage<T>
	* @author 南征
	* @date 2018年6月14日上午9:53:42
	*/ 
	public static <T> GuardRresponseMessage<T> creatBySuccess(){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode());
	}
	
	/** 
	* @Title: creatBySuccessMessage 
	* @Description: 返回成stat 和自定义消息
	* @return GuardRresponseMessage<T>
	* @author 南征
	* @date 2018年6月14日上午10:09:48
	*/ 
	public static <T> GuardRresponseMessage<T> creatBySuccessMessage(){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
	}
	/** 
	* @Title: creatBySuccessMessage 
	* @Description: 返回成功自定义message 
	* @param msg
	* @return GuardRresponseMessage<T>
	* @author 南征
	* @date 2018年6月14日上午10:10:11
	*/ 
	public static <T> GuardRresponseMessage<T> creatBySuccessMessage(String msg){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode(),msg);
	}
	/** 
	* @Title: creatBySuccess 
	* @Description: 返回成功自定义消息和data
	* @param msg
	* @param data
	* @return GuardRresponseMessage<T>
	* @author 南征
	* @date 2018年6月14日上午10:10:37
	*/ 
	public static <T> GuardRresponseMessage<T> creatBySuccess(String msg,T data){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode(),msg,data);
	}
	/** 
	* @Title: creatBySuccessData 
	* @Description: 返回成功数据
	* @param data
	* @return GuardRresponseMessage<T>
	* @author 南征
	* @date 2018年6月14日上午10:11:07
	*/ 
	public static <T> GuardRresponseMessage<T> creatBySuccessData(T data){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),data);
	}
	/** 
	* @Title: creatByError 
	* @Description: 返回失败stat
	* @return GuardRresponseMessage<T>
	* @author 南征
	* @date 2018年6月14日上午10:11:39
	*/ 
	public static <T> GuardRresponseMessage<T> creatByError(){
		return new GuardRresponseMessage<T>(ResponseCode.ERROR.getCode());
	}
	
	/** 
	* @Title: creatByErrorMessage 
	* @Description 返回失败的code和自定义消息
	* @param errorCode
	* @param errorMessage
	* @return GuardRresponseMessage<T>
	* @author 南征
	* @date 2018年6月14日上午10:20:06
	*/ 
	public static <T> GuardRresponseMessage<T> creatByErrorMessage(String errorCode,T errorMessage){
		return new GuardRresponseMessage<T>(ResponseCode.ERROR.getCode(),errorCode,errorMessage);
	}
	/** 
	* @Title: creatByErrorMessage 
	* @Description 返回自定义错误消息
	* @param errorMessage
	* @return GuardRresponseMessage<T>
	* @author 南征
	* @date 2018年6月14日上午10:13:57
	*/ 
	public static <T> GuardRresponseMessage<T> creatByErrorMessage(String errorMessage){
		return new GuardRresponseMessage<T>(ResponseCode.ERROR.getCode(),errorMessage);
	}
	
	
	
}
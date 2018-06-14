package com.freedoonline.common.response;

/**
  * 
  *<p>类名：ResponseCode.java</p>
  * @Descprition 设置返回状态码和消息
  * @author 南征
  * @version 1.0
  * @since JDK1.8
  *<p>创建日期：上午10:25:33</p>
  */
public enum ResponseCode {
	
	/**
	 * code: 0
	 * field: success
	 * @Description 成功状态码和消息
	 */
	SUCCESS(0,"success"),
	/**
	 * code: -1
	 * field: error
	 * @Description 失败状态码和消息
	 */
	ERROR(-1,"error");
	
	private int code;
	private String msg;
	
	private ResponseCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public int getCode(){
		return this.code;
	}
	public String getMsg(){
		return this.msg;
	}
	
}

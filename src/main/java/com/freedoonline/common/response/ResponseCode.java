package com.freedoonline.common.response;

public enum ResponseCode {
	SUCCESS(0,"success"),
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

package com.freedoonline.common.response;

public class GuardRresponseMessage<T> {
	private int stat;
	private String msg;
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
	public static <T> GuardRresponseMessage<T> creatBySuccess(){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode());
	}
	public static <T> GuardRresponseMessage<T> creatBySuccessMessage(){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg());
	}
	public static <T> GuardRresponseMessage<T> creatBySuccessMessage(String msg){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode(),msg);
	}
	public static <T> GuardRresponseMessage<T> creatBySuccess(String msg,T data){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode(),msg,data);
	}
	public static <T> GuardRresponseMessage<T> creatBySuccessData(T data){
		return new GuardRresponseMessage<T>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(),data);
	}
	public static <T> GuardRresponseMessage<T> creatByError(){
		return new GuardRresponseMessage<T>(ResponseCode.ERROR.getCode());
	}
	public static <T> GuardRresponseMessage<T> creatByErrorMessage(String errorCode,T errorMessage){
		return new GuardRresponseMessage<T>(ResponseCode.ERROR.getCode(),errorCode,errorMessage);
	}
	
	
	
}
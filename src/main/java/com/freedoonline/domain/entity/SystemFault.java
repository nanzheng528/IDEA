package com.freedoonline.domain.entity;

public class SystemFault {
	
	//private String objectId;
	private String systemNum;
	private String systemName;
	private String fault;			//是否故障
	private Integer total;			//总个数
	private Integer faultNum;		//故障个数
	private Integer faultRate;		//故障率
	
//	public String getObjectId() {
//		return objectId;
//	}
//	public void setObjectId(String objectId) {
//		this.objectId = objectId;
//	}
	public String getSystemNum() {
		return systemNum;
	}
	public void setSystemNum(String systemNum) {
		this.systemNum = systemNum;
	}
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getFault() {
		return fault;
	}
	public void setFault(String fault) {
		this.fault = fault;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getFaultNum() {
		return faultNum;
	}
	public void setFaultNum(Integer faultNum) {
		this.faultNum = faultNum;
	}
	public Integer getFaultRate() {
		return faultRate;
	}
	public void setFaultRate(Integer faultRate) {
		this.faultRate = faultRate;
	}
	
}

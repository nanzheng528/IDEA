package com.freedoonline.service.bo;

public class SystemFaultBo {
	private String systemNum;
	private String systemName;
	private String fault;			//是否故障
	private String equLevel;		//设备重要等级
	private Integer equNum;
	private Integer total;			//总个数
	private Integer faultNum;		//故障个数
	private Integer faultRate;		//故障率
	private Integer equLevelNum;	//高级故障个数
	private Integer totalScore;
	
	
	
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	public Integer getEquNum() {
		return equNum;
	}
	public void setEquNum(Integer equNum) {
		this.equNum = equNum;
	}
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
	public String getEquLevel() {
		return equLevel;
	}
	public void setEquLevel(String equLevel) {
		this.equLevel = equLevel;
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
	public Integer getEquLevelNum() {
		return equLevelNum;
	}
	public void setEquLevelNum(Integer equLevelNum) {
		this.equLevelNum = equLevelNum;
	}
	
	
}

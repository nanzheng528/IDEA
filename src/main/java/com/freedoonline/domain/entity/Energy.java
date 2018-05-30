package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class Energy implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private String equId;			//设备ID
	private String multimeterId;	//万能表ID
	private String eneType;			//能耗类型  1:水 2:电{21:照明,22:动力,23:空调,24:特殊} 3.气
	private Integer consumeNum;		//消耗数量
	
	private String limit;			//限定值
	private String recommend;		//推荐值
	private String optimum;			//最优值
	
	private String enpId;
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	
	
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getOptimum() {
		return optimum;
	}
	public void setOptimum(String optimum) {
		this.optimum = optimum;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getEquId() {
		return equId;
	}
	public void setEquId(String equId) {
		this.equId = equId;
	}
	public String getMultimeterId() {
		return multimeterId;
	}
	public void setMultimeterId(String multimeterId) {
		this.multimeterId = multimeterId;
	}
	public String getEneType() {
		return eneType;
	}
	public void setEneType(String eneType) {
		this.eneType = eneType;
	}

	public Integer getConsumeNum() {
		return consumeNum;
	}
	public void setConsumeNum(Integer consumeNum) {
		this.consumeNum = consumeNum;
	}
	public String getEnpId() {
		return enpId;
	}
	public void setEnpId(String enpId) {
		this.enpId = enpId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	
}

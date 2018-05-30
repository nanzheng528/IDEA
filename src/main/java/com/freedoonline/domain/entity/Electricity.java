package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 
  *<p>类描述：电。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月11日 下午4:21:45。</p>
  */
public class Electricity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private String multimeterId;//表ID
	private String billTime;	//账单日期
	private String totalPower;	//用电总量
	private String declareMd;	//申报MD
	private String actualMd;	//实际MD
	private String cost;		//费用
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	private String enpId;
	
	public String getEnpId() {
		return enpId;
	}
	public void setEnpId(String enpId) {
		this.enpId = enpId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getMultimeterId() {
		return multimeterId;
	}
	public void setMultimeterId(String multimeterId) {
		this.multimeterId = multimeterId;
	}
	public String getBillTime() {
		return billTime;
	}
	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}
	public String getTotalPower() {
		return totalPower;
	}
	public void setTotalPower(String totalPower) {
		this.totalPower = totalPower;
	}
	public String getDeclareMd() {
		return declareMd;
	}
	public void setDeclareMd(String declareMd) {
		this.declareMd = declareMd;
	}
	public String getActualMd() {
		return actualMd;
	}
	public void setActualMd(String actualMd) {
		this.actualMd = actualMd;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
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

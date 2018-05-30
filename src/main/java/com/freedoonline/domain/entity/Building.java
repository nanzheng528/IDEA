package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class Building implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private String buildingName;
	private Integer height;		//楼宇高度
	private Integer floor;		//层
	private String position;	//
	private Integer type;
	private Double acreage;
	private Integer years;
	private Date buildTime;
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	private String enpId;
	private String enpName;
	
	
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getEnpId() {
		return enpId;
	}
	public void setEnpId(String enpId) {
		this.enpId = enpId;
	}
	public String getEnpName() {
		return enpName;
	}
	public void setEnpName(String enpName) {
		this.enpName = enpName;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getFloor() {
		return floor;
	}
	public void setFloor(Integer floor) {
		this.floor = floor;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Double getAcreage() {
		return acreage;
	}
	public void setAcreage(Double acreage) {
		this.acreage = acreage;
	}
	public Integer getYears() {
		return years;
	}
	public void setYears(Integer years) {
		this.years = years;
	}
	public Date getBuildTime() {
		return buildTime;
	}
	public void setBuildTime(Date buildTime) {
		this.buildTime = buildTime;
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

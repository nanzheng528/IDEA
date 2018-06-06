package com.freedoonline.service.bo;

public class BuildingAreaBo {
	
	private String objectId;
	private String buildingId;
	private String areaNum;
	private String areaName;
	private String parentId;
	
	private Integer areaType;
	private String areaTypeCn;
	private String areaTypeEn;
	private String purpose;		//用途
	private String floor;
	private String remark;
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(String areaNum) {
		this.areaNum = areaNum;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Integer getAreaType() {
		return areaType;
	}
	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}
	public String getAreaTypeCn() {
		return areaTypeCn;
	}
	public void setAreaTypeCn(String areaTypeCn) {
		this.areaTypeCn = areaTypeCn;
	}
	public String getAreaTypeEn() {
		return areaTypeEn;
	}
	public void setAreaTypeEn(String areaTypeEn) {
		this.areaTypeEn = areaTypeEn;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
}

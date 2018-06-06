package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 
  *<p>类描述：设备设施。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月3日 下午4:26:04。</p>
  */
public class Equipment implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private String subNum;			//所属系统编号
	private String subName;			//所属系统名称
	
	private String equNum;			//设备编号
	private String equName;			//设备名称
	private Integer equLevel;		//设备重要性(等级)
	private String equModel;		//设备型号
	private String equSerialNum;	//设备系列号
	private Integer years ;			//使用年限
	private Integer quantity;		//数量
	private String purpose;			//用途
	
	private String buildingId;		//楼宇ID
	private String buildingName;	//楼宇名称
	private String buildingFloor;	//楼层
	private String buildingArea;	//楼层区域(安装区域)
	private String buildingAreaId;	//楼层区域(安装区域)
	
	private String serviceArea;		//服务区域
	private String manufacturer;	//生产厂商
	private Date manufactureTime;	//生产日期
	private String additionalFile;	//附属文件(使用说明书,配件)
	private String parameter;		//设备参数(Json存储,额定电压,额定功率开头)
	private String position;		//存放位置
	
	private Integer circleType;		//电气系统专用项
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	private String enpId;
	
	public String getBuildingAreaId() {
		return buildingAreaId;
	}
	public void setBuildingAreaId(String buildingAreaId) {
		this.buildingAreaId = buildingAreaId;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
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
	public String getSubNum() {
		return subNum;
	}
	public void setSubNum(String subNum) {
		this.subNum = subNum;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getEquNum() {
		return equNum;
	}
	public void setEquNum(String equNum) {
		this.equNum = equNum;
	}
	public String getEquName() {
		return equName;
	}
	public void setEquName(String equName) {
		this.equName = equName;
	}
	public Integer getEquLevel() {
		return equLevel;
	}
	public void setEquLevel(Integer equLevel) {
		this.equLevel = equLevel;
	}
	public String getEquModel() {
		return equModel;
	}
	public void setEquModel(String equModel) {
		this.equModel = equModel;
	}
	public String getEquSerialNum() {
		return equSerialNum;
	}
	public void setEquSerialNum(String equSerialNum) {
		this.equSerialNum = equSerialNum;
	}
	public Integer getYears() {
		return years;
	}
	public void setYears(Integer years) {
		this.years = years;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getBuildingFloor() {
		return buildingFloor;
	}
	public void setBuildingFloor(String buildingFloor) {
		this.buildingFloor = buildingFloor;
	}
	public String getBuildingArea() {
		return buildingArea;
	}
	public void setBuildingArea(String buildingArea) {
		this.buildingArea = buildingArea;
	}
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public Date getManufactureTime() {
		return manufactureTime;
	}
	public void setManufactureTime(Date manufactureTime) {
		this.manufactureTime = manufactureTime;
	}
	public String getAdditionalFile() {
		return additionalFile;
	}
	public void setAdditionalFile(String additionalFile) {
		this.additionalFile = additionalFile;
	}
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
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
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Integer getCircleType() {
		return circleType;
	}
	public void setCircleType(Integer circleType) {
		this.circleType = circleType;
	}
	
}

package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 
  *<p>类描述：万用表。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月8日 下午3:39:17。</p>
 */
public class Meter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;		
	private String buildingId;
	private String name;				//表名
	private String number;				//编号
	private Integer type;				//表的类型
	private Integer energyType;			//能耗类别
//	private String floor;				//楼层
//	private String location;			//位置描述
	private String buildingAreaId;		//楼宇区域ID
	private String serviceArea;			//服务区域
	private String ulAlarm;				//上限警报值
	private String llAlarm;				//下限警报值
	private Integer status;				//运行状态
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	private String enpId;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
//	public String getFloor() {
//		return floor;
//	}
//	public void setFloor(String floor) {
//		this.floor = floor;
//	}
//	public String getLocation() {
//		return location;
//	}
//	public void setLocation(String location) {
//		this.location = location;
//	}
	
	public String getUlAlarm() {
		return ulAlarm;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setEnergyType(Integer energyType) {
		this.energyType = energyType;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setEnergyType(int energyType) {
		this.energyType = energyType;
	}
	public Integer getType() {
		return type;
	}
	public Integer getEnergyType() {
		return energyType;
	}
	public String getBuildingAreaId() {
		return buildingAreaId;
	}
	public void setBuildingAreaId(String buildingAreaId) {
		this.buildingAreaId = buildingAreaId;
	}
	public void setUlAlarm(String ulAlarm) {
		this.ulAlarm = ulAlarm;
	}
	public String getLlAlarm() {
		return llAlarm;
	}
	public void setLlAlarm(String llAlarm) {
		this.llAlarm = llAlarm;
	}
	public String getServiceArea() {
		return serviceArea;
	}
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
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
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnpId() {
		return enpId;
	}
	public void setEnpId(String enpId) {
		this.enpId = enpId;
	}
	
}

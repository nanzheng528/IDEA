package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 
  *<p>类描述：(合规性)楼宇人员配置。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年5月1日 下午9:12:12。</p>
  */
public class BuildingStaffing implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;		//楼宇人员配置实体
	private String buildingId;		//楼宇ID
	private String buildingName;	//楼宇名称
	private Integer typeNum;		//工作类型编号
	private String typeName;		//工作类型名称
	private String maintenaceId;	//人员ID
	private String maintenaceName;	//人员名称
	private Integer standard;		//标准人数
	private Integer actual;			//实际人数
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	
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
	public Integer getTypeNum() {
		return typeNum;
	}
	public void setTypeNum(Integer typeNum) {
		this.typeNum = typeNum;
	}
	public Integer getStandard() {
		return standard;
	}
	public void setStandard(Integer standard) {
		this.standard = standard;
	}
	public Integer getActual() {
		return actual;
	}
	public void setActual(Integer actual) {
		this.actual = actual;
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
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getMaintenaceId() {
		return maintenaceId;
	}
	public void setMaintenaceId(String maintenaceId) {
		this.maintenaceId = maintenaceId;
	}
	public String getMaintenaceName() {
		return maintenaceName;
	}
	public void setMaintenaceName(String maintenaceName) {
		this.maintenaceName = maintenaceName;
	}
	
}

package com.freedoonline.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
  * 
  *<p>类描述：室内健康。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月29日 上午11:13:23。</p>
  */
public class Hiecs implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String sign;
	private String objectId;
	private String buildingId;			//楼宇ID
	private String floor;				//楼层
	private String area;				//楼层区域
	private String temperature;			//温度
	private String humidity;			//湿度
	private String co2;					//二氧化碳
	private String pm;					//PM2.5
	private String hcho;				//甲醛
	private String tvoc;				//室内空气品质的研究人员通常把他们采样分析的所有室内有机气态物质称为TVOC
	private String nh3;					//氨
	private Double score;
	
	private String createUser;
	private Date createTime;
	private String modifyUser;
	private Date modifyTime;
	private String remark;
	private Integer active;
	
	public String getTvoc() {
		return tvoc;
	}
	public void setTvoc(String tvoc) {
		this.tvoc = tvoc;
	}
	public String getNh3() {
		return nh3;
	}
	public void setNh3(String nh3) {
		this.nh3 = nh3;
	}
	public String getHcho() {
		return hcho;
	}
	public void setHcho(String hcho) {
		this.hcho = hcho;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
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
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getCo2() {
		return co2;
	}
	public void setCo2(String co2) {
		this.co2 = co2;
	}
	public String getPm() {
		return pm;
	}
	public void setPm(String pm) {
		this.pm = pm;
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
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
}

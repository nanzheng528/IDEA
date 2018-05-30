package com.freedoonline.domain.entity;

import java.io.Serializable;

public class EquipmentRunTime implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String objectId;
	private String equId;
	private String equName;
	private Integer runStatus;		//1运行，-1停止，0故障
	private Double current;			//实时电流
	private Double power;			//实时功率
	private Double totalPawer;		//累计功率
	
	
}

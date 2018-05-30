package com.freedoonline.service.bo;

public class HiecsStandard {
	
	private Integer score;  	//分数
	private String maxStandard;	//标准
	private String minStandard;	//标准
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getMaxStandard() {
		return maxStandard;
	}
	public void setMaxStandard(String maxStandard) {
		this.maxStandard = maxStandard;
	}
	public String getMinStandard() {
		return minStandard;
	}
	public void setMinStandard(String minStandard) {
		this.minStandard = minStandard;
	}
	public HiecsStandard(Integer score, String maxStandard, String minStandard) {
		super();
		this.score = score;
		this.maxStandard = maxStandard;
		this.minStandard = minStandard;
	}
	
}

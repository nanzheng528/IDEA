package com.freedoonline.service;

import java.util.List;
import java.util.Map;

import javax.management.Query;

import com.freedoonline.domain.entity.Process;

public interface ProcessService {
	//添加流程
	String addProcess(Process process);
	//通过列名查询人员列表
	List<Map<String, Object>> queryUserListByCloumnName(Map<String, Object> map);
	//查询流程
	List<Map<String, Object>> queryProcess(Process process);
	//更新流程
	String updateProcess(Process process);
}

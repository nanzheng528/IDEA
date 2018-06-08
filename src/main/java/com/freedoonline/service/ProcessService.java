package com.freedoonline.service;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Process;

public interface ProcessService {
	
	String addProcess(Process process);
	
	List<Map<String, Object>>queryUserListByCloumnName(Map<String, Object> map);
}

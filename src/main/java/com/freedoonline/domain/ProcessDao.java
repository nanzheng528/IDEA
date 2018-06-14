package com.freedoonline.domain;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Process;

public interface ProcessDao {

	String addProcess(Process process);
	
	List<Map<String, Object>> selectUserByColumnName(Map<String, Object> map);
	
	List<Map<String, Object>> queryProcess(Process process);
}

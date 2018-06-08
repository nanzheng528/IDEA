package com.freedoonline.domain;

import java.util.List;
import java.util.Map;

import com.freedoonline.domain.entity.Process;

public interface ProcessDao {

	String addProcess(Process process);
	
	List<Map<String,Object>> selectUserById(Map<String, Object> map);
}

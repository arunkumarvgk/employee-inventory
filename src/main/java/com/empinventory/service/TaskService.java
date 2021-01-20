package com.empinventory.service;

import org.springframework.stereotype.Service;

@Service
public interface TaskService {
	int createTask();
	void updateTask(int id, String status);
	String getStatus(int id);
}

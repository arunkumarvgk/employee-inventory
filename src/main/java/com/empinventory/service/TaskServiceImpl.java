package com.empinventory.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empinventory.model.Task;
import com.empinventory.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public int createTask() {
		return taskRepository.save(new Task("Task Created")).getId();
	}

	@Override
	public void updateTask(final int id, final String status) {
		final Optional<Task> task = taskRepository.findById(id);
		if (task.isPresent()) {
			final Task t = task.get();
			t.setStatus(status);
			taskRepository.save(t);
		}
	}

	@Override
	public String getStatus(final int id) {
		final Optional<Task> task = taskRepository.findById(id);
		if (task.isPresent()) {
			return task.get().getStatus();
		}
		return null;
	}
}

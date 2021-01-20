package com.empinventory.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Task Enity class
 */
@Entity
public class Task {

	@Id
	@GeneratedValue
	private int id;
	private String status;

	public Task() {}
	public Task(final String status) {
		super();
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(final String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", status=" + status + "]";
	}
}

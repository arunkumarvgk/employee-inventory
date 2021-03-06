package com.empinventory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.empinventory.model.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer>{

}

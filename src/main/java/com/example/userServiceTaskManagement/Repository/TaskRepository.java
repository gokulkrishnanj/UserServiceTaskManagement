package com.example.userServiceTaskManagement.Repository;

import com.example.userServiceTaskManagement.Entity.Student;
import com.example.userServiceTaskManagement.Entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,String> {
    Optional<Task> findByTaskNameAndStudentStudentId(String taskName, String studentId);
}

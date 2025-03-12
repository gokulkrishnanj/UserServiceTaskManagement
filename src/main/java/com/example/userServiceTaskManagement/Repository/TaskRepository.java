package com.example.userServiceTaskManagement.Repository;

import com.example.userServiceTaskManagement.Constants.StatusName;
import com.example.userServiceTaskManagement.Entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, String> {
    Optional<Task> findByTaskNameContainingIgnoreCaseAndStudentStudentIdAndSubjectSubjectNameContainingIgnoreCase(String taskName, String studentId, String subject);

    Page<Task> findByStudentStudentId(String studentId, Pageable pageable);

    Page<Task> findByTaskNameContainingIgnoreCase(String taskName, Pageable pageable);

    Page<Task> findByStudentStudentIdAndTaskNameContainingIgnoreCaseAndTaskStatusIdStatusNameIn(String studentId, String taskName, List<StatusName> statusNameList, Pageable pageable);

    Page<Task> findByStudentStudentIdAndTaskStatusIdStatusNameIn(String studentId, List<StatusName> statusNameList, Pageable pageable);
}

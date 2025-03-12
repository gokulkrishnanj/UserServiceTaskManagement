package com.example.userServiceTaskManagement.Repository;

import com.example.userServiceTaskManagement.Constants.StatusName;
import com.example.userServiceTaskManagement.Entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, String> {
    TaskStatus findByStatusName(StatusName statusName);
}

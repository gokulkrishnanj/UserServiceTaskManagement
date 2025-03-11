package com.example.userServiceTaskManagement.DTO;

import com.example.userServiceTaskManagement.Entity.Subject;
import com.example.userServiceTaskManagement.Entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private String message;
    private String taskId;
    private String taskName;
    private String taskDescription;
    private String studentId;
    private TaskStatus taskStatus;
    private Subject subject;
}

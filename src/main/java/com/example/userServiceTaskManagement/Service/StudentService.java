package com.example.userServiceTaskManagement.Service;

import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;

public interface StudentService {
    public TaskResponseDTO addTasks(TaskDTO taskDTO);
}

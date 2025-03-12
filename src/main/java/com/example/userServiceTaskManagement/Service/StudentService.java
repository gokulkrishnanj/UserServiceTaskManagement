package com.example.userServiceTaskManagement.Service;

import com.example.userServiceTaskManagement.DTO.ChangeStatusDTO;
import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import com.example.userServiceTaskManagement.DTO.ViewTaskResponseDTO;

import java.util.List;

public interface StudentService {
    public TaskResponseDTO addTasks(TaskDTO taskDTO);

    public TaskResponseDTO changeStatus(ChangeStatusDTO changeStatusDTO);

    public TaskResponseDTO deleteTask(String taskName, String subjectName);

    public List<TaskResponseDTO> getAllTasks(Integer itemsPerPage, Integer pageNumber);

    public ViewTaskResponseDTO viewTaskWithFilter(Integer itemsPerPage, Integer pageNumber, String status, String taskName);
}

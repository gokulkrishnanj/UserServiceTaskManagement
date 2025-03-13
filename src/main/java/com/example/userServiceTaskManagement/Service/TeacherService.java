package com.example.userServiceTaskManagement.Service;

import com.example.userServiceTaskManagement.DTO.StudentDetailsDTO;
import com.example.userServiceTaskManagement.DTO.ViewTaskResponseDTO;

import java.util.List;

public interface TeacherService {

    public List<StudentDetailsDTO> getAllStudents(String studentName, Integer pageNumber, Integer itemsPerPage);

    public ViewTaskResponseDTO viewStudentTask(String studentName, String status, Integer pageNumber, Integer itemsPerPage);

}

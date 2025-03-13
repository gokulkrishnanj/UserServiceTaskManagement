package com.example.userServiceTaskManagement.API;


import com.example.userServiceTaskManagement.DTO.StudentDetailsDTO;
import com.example.userServiceTaskManagement.DTO.ViewTaskResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(value = "api/v1/teacher")
public interface TeacherAPI {

    @RequestMapping(value = "/getStudent")
    @PreAuthorize(("hasRole('TEACHER')"))
    public ResponseEntity<List<StudentDetailsDTO>> getAllStudents(@RequestParam(value = "studentName", required = false) String studentName,
                                                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                                  @RequestParam(value = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage);

    @RequestMapping(value = "/viewStudentTask")
    @PreAuthorize(("hasRole('TEACHER')"))
    public ResponseEntity<ViewTaskResponseDTO> viewStudentTask(@RequestParam(value = "studentName") String studentName,
                                                               @RequestParam(value = "status", required = false) String status,
                                                               @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                               @RequestParam(value = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage);

}

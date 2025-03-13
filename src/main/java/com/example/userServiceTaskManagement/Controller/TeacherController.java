package com.example.userServiceTaskManagement.Controller;

import com.example.userServiceTaskManagement.API.TeacherAPI;
import com.example.userServiceTaskManagement.DTO.StudentDetailsDTO;
import com.example.userServiceTaskManagement.DTO.ViewTaskResponseDTO;
import com.example.userServiceTaskManagement.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeacherController implements TeacherAPI {

    @Autowired
    private TeacherService teacherService;

    public ResponseEntity<List<StudentDetailsDTO>> getAllStudents(String studentName, Integer pageNumber, Integer itemsPerPage) {
        return new ResponseEntity<>(teacherService.getAllStudents(studentName, pageNumber, itemsPerPage), HttpStatus.OK);
    }

    public ResponseEntity<ViewTaskResponseDTO> viewStudentTask(String studentName, String status, Integer pageNumber, Integer itemsPerPage) {
        return new ResponseEntity<>(teacherService.viewStudentTask(studentName, status, pageNumber, itemsPerPage), HttpStatus.OK);
    }

}

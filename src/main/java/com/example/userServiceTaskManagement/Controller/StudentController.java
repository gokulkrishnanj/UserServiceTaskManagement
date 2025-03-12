package com.example.userServiceTaskManagement.Controller;

import com.example.userServiceTaskManagement.API.StudentAPI;
import com.example.userServiceTaskManagement.DTO.ChangeStatusDTO;
import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import com.example.userServiceTaskManagement.DTO.ViewTaskResponseDTO;
import com.example.userServiceTaskManagement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController implements StudentAPI {

    @Autowired
    private StudentService studentService;

    public ResponseEntity<TaskResponseDTO> addTasks(TaskDTO taskDTO) {
        return new ResponseEntity<>(studentService.addTasks(taskDTO), HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<TaskResponseDTO> changeStatus(ChangeStatusDTO changeStatusDTO) {
        return new ResponseEntity<>(studentService.changeStatus(changeStatusDTO), HttpStatus.OK);
    }

    public ResponseEntity<TaskResponseDTO> deleteTask(String taskName, String subjectName) {
        return new ResponseEntity<>(studentService.deleteTask(taskName, subjectName), HttpStatus.OK);
    }

    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(Integer itemsPerPage, Integer pageNumber) {
        return new ResponseEntity<>(studentService.getAllTasks(itemsPerPage, pageNumber), HttpStatus.OK);
    }

    public ResponseEntity<ViewTaskResponseDTO> viewTaskWithFilter(Integer itemsPerPage, Integer pageNumber, String status, String taskName) {
        return new ResponseEntity<>(studentService.viewTaskWithFilter(itemsPerPage, pageNumber, status, taskName), HttpStatus.OK);
    }

}

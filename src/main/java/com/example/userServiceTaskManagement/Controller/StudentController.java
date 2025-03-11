package com.example.userServiceTaskManagement.Controller;

import com.example.userServiceTaskManagement.API.StudentAPI;
import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import com.example.userServiceTaskManagement.Entity.Task;
import com.example.userServiceTaskManagement.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController implements StudentAPI {

    @Autowired
    private StudentService studentService;

    public ResponseEntity<TaskResponseDTO> addTasks(TaskDTO taskDTO){
        return new ResponseEntity<>(studentService.addTasks(taskDTO), HttpStatusCode.valueOf(200));
    }

    public ResponseEntity<TaskResponseDTO> changeStatus(String taskName, String status){
        return new ResponseEntity<>(studentService.changeStatus(taskName,status), HttpStatus.OK);
    }

    public ResponseEntity<TaskResponseDTO> deleteTask(String taskName){
        return new ResponseEntity<>(studentService.deleteTask(taskName),HttpStatus.OK);
    }
}

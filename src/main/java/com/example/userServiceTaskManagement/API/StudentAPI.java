package com.example.userServiceTaskManagement.API;


import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "student/api/v1")
public interface StudentAPI {

    @PostMapping(value = "/addTasks")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TaskResponseDTO> addTasks(@Validated @RequestBody TaskDTO taskDTO);
}

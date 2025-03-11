package com.example.userServiceTaskManagement.API;


import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "student/api/v1")
public interface StudentAPI {

    @PostMapping(value = "/addTasks")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TaskResponseDTO> addTasks(@Validated @RequestBody TaskDTO taskDTO);

    @PutMapping(value = "/changeStatus")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TaskResponseDTO> changeStatus(@RequestParam(value = "taskName") String taskName, @RequestParam(value = "status") String status);

    @DeleteMapping(value = "/deleteTasks")
    @PreAuthorize(("hasRole('STUDENT')"))
    public ResponseEntity<TaskResponseDTO> deleteTask(@RequestParam(value = "taskName") String taskName);
}

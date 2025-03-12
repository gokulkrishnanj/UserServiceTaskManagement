package com.example.userServiceTaskManagement.API;


import com.example.userServiceTaskManagement.DTO.ChangeStatusDTO;
import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import com.example.userServiceTaskManagement.DTO.ViewTaskResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "student/api/v1")
public interface StudentAPI {

    @PostMapping(value = "/addTasks")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TaskResponseDTO> addTasks(@Validated @RequestBody TaskDTO taskDTO);

    @PutMapping(value = "/changeStatus")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<TaskResponseDTO> changeStatus(@RequestBody ChangeStatusDTO changeStatusDTO);

    @DeleteMapping(value = "/deleteTasks")
    @PreAuthorize(("hasRole('STUDENT')"))
    public ResponseEntity<TaskResponseDTO> deleteTask(@RequestParam(value = "taskName") String taskName,
                                                      @RequestParam(value = "subjectName") String subjectName);

    @GetMapping(value = "/getAllTasks")
    @PreAuthorize(("hasRole('STUDENT')"))
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(@RequestParam(value = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage,
                                                             @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber);

    @GetMapping(value = "/filterTask")
    @PreAuthorize(("hasRole('STUDENT')"))
    public ResponseEntity<ViewTaskResponseDTO> viewTaskWithFilter(@RequestParam(value = "itemsPerPage", required = false, defaultValue = "10") Integer itemsPerPage,
                                                                  @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                                  @RequestParam(value = "status", required = false) String status,
                                                                  @RequestParam(value = "taskName", required = false) String taskName);

}

package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.Constants.ApplicationConstants;
import com.example.userServiceTaskManagement.Constants.StatusName;
import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import com.example.userServiceTaskManagement.Entity.*;
import com.example.userServiceTaskManagement.Repository.*;
import com.example.userServiceTaskManagement.Service.StudentService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private TaskRepository taskRepository;
    private UserDetailRepository userDetailRepository;
    private SubjectRepository subjectRepository;
    private TaskStatusRepository taskStatusRepository;

    public StudentServiceImpl(StudentRepository studentRepository, TaskRepository taskRepository, UserDetailRepository userDetailRepository, SubjectRepository subjectRepository, TaskStatusRepository taskStatusRepository){
        this.studentRepository = studentRepository;
        this.taskRepository = taskRepository;
        this.subjectRepository = subjectRepository;
        this.userDetailRepository = userDetailRepository;
        this.taskStatusRepository = taskStatusRepository;
    }

    @Override
    public TaskResponseDTO addTasks(TaskDTO taskDTO){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserDetail userDetail = userDetailRepository.findByUserMailId(userName);
        Student student = studentRepository.findByUserDetailUserMailId(userDetail.getUserMailId());
        Optional<Task> optionalTask = taskRepository.findByTaskNameAndStudentStudentId(taskDTO.getTaskName(),student.getStudentId());
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        if(optionalTask.isPresent()){
            taskResponseDTO.setMessage(ApplicationConstants.TASK_EXISTS);
            return setTaskResponseDTO(optionalTask,taskResponseDTO);
        }
        Task task = new Task();
        task.setTaskName(taskDTO.getTaskName());
        task.setTaskDescription(taskDTO.getTaskDescription());
        task.setStudent(student);
        TaskStatus taskStatus = taskStatusRepository.findByStatusName(StatusName.YET_TO_START);
        task.setTaskStatusId(taskStatus);
        Subject subject = subjectRepository.findBySubjectName(taskDTO.getSubject().toUpperCase());
        task.setSubject(subject);
        taskRepository.save(task);
        taskResponseDTO.setMessage(ApplicationConstants.TASK_CREATED);
        return  setTaskResponseDTO(Optional.of(task),taskResponseDTO);
    }

    private TaskResponseDTO setTaskResponseDTO(Optional<Task> optionalTask, TaskResponseDTO taskResponseDTO){
        taskResponseDTO.setTaskId(optionalTask.get().getTaskId());
        taskResponseDTO.setTaskName(optionalTask.get().getTaskName());
        taskResponseDTO.setTaskDescription(optionalTask.get().getTaskDescription());
        taskResponseDTO.setSubject(optionalTask.get().getSubject());
        taskResponseDTO.setStudentId(optionalTask.get().getStudent().getStudentId());
        taskResponseDTO.setTaskStatus(optionalTask.get().getTaskStatusId());
        return  taskResponseDTO;
    }

}

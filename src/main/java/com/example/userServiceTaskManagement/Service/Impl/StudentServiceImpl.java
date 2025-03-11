package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.Constants.ApplicationConstants;
import com.example.userServiceTaskManagement.Constants.StatusName;
import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import com.example.userServiceTaskManagement.Entity.*;
import com.example.userServiceTaskManagement.Exceptions.TasksNotFoundException;
import com.example.userServiceTaskManagement.Repository.*;
import com.example.userServiceTaskManagement.Service.StudentService;
import com.example.userServiceTaskManagement.Constants.Utils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private TaskRepository taskRepository;
    private UserDetailRepository userDetailRepository;
    private SubjectRepository subjectRepository;
    private TaskStatusRepository taskStatusRepository;
    private Utils utils;

    public StudentServiceImpl(StudentRepository studentRepository, Utils utils,TaskRepository taskRepository, UserDetailRepository userDetailRepository, SubjectRepository subjectRepository, TaskStatusRepository taskStatusRepository){
        this.studentRepository = studentRepository;
        this.taskRepository = taskRepository;
        this.subjectRepository = subjectRepository;
        this.userDetailRepository = userDetailRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.utils = utils;
    }

    @Override
    public TaskResponseDTO addTasks(TaskDTO taskDTO){
        String userName = utils.getUserNameFromToken();
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

    @Override
    public TaskResponseDTO changeStatus(String taskName, String status){
        String userName = utils.getUserNameFromToken();
        UserDetail userDetail = userDetailRepository.findByUserMailId(userName);
        Student student = studentRepository.findByUserDetailUserMailId(userDetail.getUserMailId());
        Optional<Task> optionalTask = taskRepository.findByTaskNameAndStudentStudentId(taskName, student.getStudentId());
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        if(optionalTask.isPresent()){
            Task task = optionalTask.get();
            TaskStatus taskStatus = task.getTaskStatusId();
            String oldStatus = taskStatus.getStatusName().toString();
            TaskStatus newtaskStatus = taskStatusRepository.findByStatusName(StatusName.valueOf(status));
            if(Objects.equals(taskStatus.getRank(), newtaskStatus.getRank())){
                taskResponseDTO.setMessage("Already the status is "+status);
                taskResponseDTO.setTaskName(task.getTaskName());
            }
            else if(taskStatus.getRank()> newtaskStatus.getRank()){
                taskResponseDTO.setMessage("Cannot change from "+taskStatus.getStatusName().toString()+" to "+newtaskStatus.getStatusName().toString());
            }
            else if(isValidStatusChange(status, oldStatus)){
                task.setTaskStatusId(newtaskStatus);
                taskRepository.save(task);
                taskResponseDTO.setMessage("Task update to "+status);
                taskResponseDTO.setTaskName(task.getTaskName());
            }
        }
        else{
            throw new TasksNotFoundException("Task with "+taskName+" not exists");
        }
        return taskResponseDTO;
    }

    @Override
    public TaskResponseDTO deleteTask(String taskName){
        String userName = utils.getUserNameFromToken();
        UserDetail userDetail = userDetailRepository.findByUserMailId(userName);
        Student student = studentRepository.findByUserDetailUserMailId(userDetail.getUserMailId());
        Optional<Task> optionalTask = taskRepository.findByTaskNameAndStudentStudentId(taskName, student.getStudentId());
        if(optionalTask.isEmpty()){
            throw new TasksNotFoundException(taskName+" not exists");
        }
        Task task = optionalTask.get();
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setTaskName(task.getTaskName());
        taskResponseDTO.setSubject(task.getSubject());
        taskResponseDTO.setTaskDescription(task.getTaskDescription());
        taskResponseDTO.setTaskId(task.getTaskId());
        taskRepository.delete(task);
        taskResponseDTO.setMessage("Task Deleted successfully");
        return taskResponseDTO;
    }

    private boolean isValidStatusChange(String newStatus, String oldStatus){
        return ((newStatus.equals(ApplicationConstants.IN_PROGRESS) && oldStatus.equals(ApplicationConstants.YET_TO_START)) || (newStatus.equals(ApplicationConstants.COMPLETED) && oldStatus.equals(ApplicationConstants.IN_PROGRESS)));
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

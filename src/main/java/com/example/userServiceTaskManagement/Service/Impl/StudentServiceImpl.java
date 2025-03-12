package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.Constants.ApplicationConstants;
import com.example.userServiceTaskManagement.Constants.StatusName;
import com.example.userServiceTaskManagement.DTO.ChangeStatusDTO;
import com.example.userServiceTaskManagement.DTO.TaskDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import com.example.userServiceTaskManagement.DTO.ViewTaskResponseDTO;
import com.example.userServiceTaskManagement.Entity.*;
import com.example.userServiceTaskManagement.Exceptions.TasksNotFoundException;
import com.example.userServiceTaskManagement.Repository.*;
import com.example.userServiceTaskManagement.Service.StudentService;
import com.example.userServiceTaskManagement.Constants.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private TaskRepository taskRepository;
    private UserDetailRepository userDetailRepository;
    private SubjectRepository subjectRepository;
    private TaskStatusRepository taskStatusRepository;
    private Utils utils;

    public StudentServiceImpl(StudentRepository studentRepository, Utils utils, TaskRepository taskRepository, UserDetailRepository userDetailRepository, SubjectRepository subjectRepository, TaskStatusRepository taskStatusRepository) {
        this.studentRepository = studentRepository;
        this.taskRepository = taskRepository;
        this.subjectRepository = subjectRepository;
        this.userDetailRepository = userDetailRepository;
        this.taskStatusRepository = taskStatusRepository;
        this.utils = utils;
    }

    @Override
    public TaskResponseDTO addTasks(TaskDTO taskDTO) {
        Student student = getStudentDetailsFromToken();
        Optional<Task> optionalTask = taskRepository.findByTaskNameContainingIgnoreCaseAndStudentStudentIdAndSubjectSubjectNameContainingIgnoreCase(taskDTO.getTaskName(), student.getStudentId(), taskDTO.getSubject());
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        if (optionalTask.isPresent()) {
            taskResponseDTO.setMessage(ApplicationConstants.TASK_EXISTS);
            return setTaskResponseDTO(optionalTask, taskResponseDTO);
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
        return setTaskResponseDTO(Optional.of(task), taskResponseDTO);
    }

    @Override
    public TaskResponseDTO changeStatus(ChangeStatusDTO changeStatusDTO) {
        Student student = getStudentDetailsFromToken();
        Optional<Task> optionalTask = taskRepository.findByTaskNameContainingIgnoreCaseAndStudentStudentIdAndSubjectSubjectNameContainingIgnoreCase(changeStatusDTO.getTaskName(), student.getStudentId(), changeStatusDTO.getSubjectName());
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            log.info("taskName:" + task.getTaskName() + " subjectName:" + task.getSubject().getSubjectName() + " status:" + task.getTaskStatusId().getStatusName());
            TaskStatus taskStatus = task.getTaskStatusId();
            String oldStatus = taskStatus.getStatusName().toString();
            TaskStatus newtaskStatus = taskStatusRepository.findByStatusName(StatusName.valueOf(changeStatusDTO.getStatus()));
            if (Objects.equals(taskStatus.getRank(), newtaskStatus.getRank())) {
                taskResponseDTO.setMessage("Already the status is " + changeStatusDTO.getStatus());
                taskResponseDTO.setTaskName(task.getTaskName());
            } else if (taskStatus.getRank() > newtaskStatus.getRank()) {
                taskResponseDTO.setMessage("Cannot change from " + taskStatus.getStatusName().toString() + " to " + newtaskStatus.getStatusName().toString());
            } else if (isValidStatusChange(changeStatusDTO.getStatus(), oldStatus)) {
                task.setTaskStatusId(newtaskStatus);
                taskRepository.save(task);
                taskResponseDTO.setMessage("Task update to " + changeStatusDTO.getStatus());
                taskResponseDTO.setTaskName(task.getTaskName());
            }
        } else {
            throw new TasksNotFoundException("Task with " + changeStatusDTO.getStatus() + " not exists");
        }
        return taskResponseDTO;
    }

    @Override
    public TaskResponseDTO deleteTask(String taskName, String subjectName) {
        Student student = getStudentDetailsFromToken();
        Optional<Task> optionalTask = taskRepository.findByTaskNameContainingIgnoreCaseAndStudentStudentIdAndSubjectSubjectNameContainingIgnoreCase(taskName, student.getStudentId(), subjectName);
        if (optionalTask.isEmpty()) {
            throw new TasksNotFoundException(taskName + " not exists");
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

    @Override
    public List<TaskResponseDTO> getAllTasks(Integer itemsPerPage, Integer pageNumber) {
        Student student = getStudentDetailsFromToken();
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
        return getAllTasksOfStudent(student, pageable);
    }

    @Override
    public ViewTaskResponseDTO viewTaskWithFilter(Integer itemsPerPage, Integer pageNumber, String status, String taskName) {
        Student student = getStudentDetailsFromToken();
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
        ViewTaskResponseDTO viewTaskResponseDTO = new ViewTaskResponseDTO();
        List<TaskResponseDTO> taskResponseDTOList = new ArrayList<>();
        List<StatusName> statusNamesList = new ArrayList<>();
        List<String> statusList = new ArrayList<>();
        if (status != null && taskName != null) {
            statusNamesList.add(StatusName.valueOf(status));
            Page<Task> taskPage = taskRepository.findByStudentStudentIdAndTaskNameContainingIgnoreCaseAndTaskStatusIdStatusNameIn(student.getStudentId(),
                    taskName, statusNamesList, pageable);
            taskResponseDTOList = taskPage.stream().map(task -> new TaskResponseDTO("null", task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getStudent().getStudentId(), task.getTaskStatusId(), task.getSubject())).collect(Collectors.toList());
            statusList.add(status);
        } else if (status != null) {
            statusNamesList.add(StatusName.valueOf(status));
            Page<Task> taskPage = taskRepository.findByStudentStudentIdAndTaskStatusIdStatusNameIn(student.getStudentId(), statusNamesList, pageable);
            taskResponseDTOList = taskPage.stream().map(task -> new TaskResponseDTO("null", task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getStudent().getStudentId(), task.getTaskStatusId(), task.getSubject())).collect(Collectors.toList());
            statusList.add(status);
        } else if (taskName != null) {
            Page<Task> taskPage = taskRepository.findByTaskNameContainingIgnoreCase(taskName, pageable);
            taskResponseDTOList = taskPage.stream().map(task -> new TaskResponseDTO("null", task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getStudent().getStudentId(), task.getTaskStatusId(), task.getSubject())).collect(Collectors.toList());
            statusList = List.of(ApplicationConstants.COMPLETED, ApplicationConstants.YET_TO_START, ApplicationConstants.IN_PROGRESS);
        } else {
            taskResponseDTOList = getAllTasksOfStudent(student, pageable);
            statusList = List.of(ApplicationConstants.COMPLETED, ApplicationConstants.YET_TO_START, ApplicationConstants.IN_PROGRESS);
        }
        viewTaskResponseDTO.setTaskStatusList(statusList);
        viewTaskResponseDTO.setTaskResponseDTOList(taskResponseDTOList);
        viewTaskResponseDTO.setItemsPerPage(itemsPerPage);
        viewTaskResponseDTO.setPageNumber(pageNumber);
        return viewTaskResponseDTO;
    }

    private List<TaskResponseDTO> getAllTasksOfStudent(Student student, Pageable pageable) {
        Page<Task> taskPage = taskRepository.findByStudentStudentId(student.getStudentId(), pageable);
        log.info("totalPage:" + taskPage.getTotalPages() + "totalContent:" + taskPage.getTotalElements());
        List<TaskResponseDTO> taskResponseDTOList = taskPage.stream().map(task -> new TaskResponseDTO(null, task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getStudent().getStudentId(), task.getTaskStatusId(), task.getSubject())).collect(Collectors.toList());
        return taskResponseDTOList;
    }

    private boolean isValidStatusChange(String newStatus, String oldStatus) {
        return ((newStatus.equals(ApplicationConstants.IN_PROGRESS) && oldStatus.equals(ApplicationConstants.YET_TO_START)) || (newStatus.equals(ApplicationConstants.COMPLETED) && oldStatus.equals(ApplicationConstants.IN_PROGRESS)));
    }

    private TaskResponseDTO setTaskResponseDTO(Optional<Task> optionalTask, TaskResponseDTO taskResponseDTO) {
        taskResponseDTO.setTaskId(optionalTask.get().getTaskId());
        taskResponseDTO.setTaskName(optionalTask.get().getTaskName());
        taskResponseDTO.setTaskDescription(optionalTask.get().getTaskDescription());
        taskResponseDTO.setSubject(optionalTask.get().getSubject());
        taskResponseDTO.setStudentId(optionalTask.get().getStudent().getStudentId());
        taskResponseDTO.setTaskStatus(optionalTask.get().getTaskStatusId());
        return taskResponseDTO;
    }

    private Student getStudentDetailsFromToken() {
        String userName = utils.getUserNameFromToken();
        UserDetail userDetail = userDetailRepository.findByUserMailId(userName);
        Student student = studentRepository.findByUserDetailUserMailId(userDetail.getUserMailId());
        return student;
    }

}

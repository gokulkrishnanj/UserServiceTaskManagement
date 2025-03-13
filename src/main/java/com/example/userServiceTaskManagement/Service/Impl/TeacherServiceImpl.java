package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.Constants.ApplicationConstants;
import com.example.userServiceTaskManagement.Constants.StatusName;
import com.example.userServiceTaskManagement.DTO.StudentDetailsDTO;
import com.example.userServiceTaskManagement.DTO.TaskResponseDTO;
import com.example.userServiceTaskManagement.DTO.ViewTaskResponseDTO;
import com.example.userServiceTaskManagement.Entity.Student;
import com.example.userServiceTaskManagement.Entity.Task;
import com.example.userServiceTaskManagement.Entity.UserDetail;
import com.example.userServiceTaskManagement.Repository.StudentRepository;
import com.example.userServiceTaskManagement.Repository.TaskRepository;
import com.example.userServiceTaskManagement.Repository.UserDetailRepository;
import com.example.userServiceTaskManagement.Service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<StudentDetailsDTO> getAllStudents(String studentName, Integer pageNumber, Integer itemsPerPage) {
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
        List<StudentDetailsDTO> studentDetailsDTOList = new ArrayList<>();
        if (studentName != null) {
            Page<UserDetail> userDetailPage = userDetailRepository.findByUserNameContainingIgnoreCase(studentName, pageable);
            studentDetailsDTOList = userDetailPage.stream().map(userDetail -> new StudentDetailsDTO(userDetail.getUserMailId(), userDetail.getUserName(), studentRepository.findByUserDetailUserMailId(userDetail.getUserMailId()).getStudentId())).collect(Collectors.toList());
        } else {
            Page<Student> studentPage = studentRepository.findAll(pageable);
            studentDetailsDTOList = studentPage.stream().map(student -> new StudentDetailsDTO(student.getUserDetail().getUserMailId(), student.getUserDetail().getUserName(), student.getStudentId())).collect(Collectors.toList());
        }
        return studentDetailsDTOList;
    }

    @Override
    public ViewTaskResponseDTO viewStudentTask(String studentName, String status, Integer pageNumber, Integer itemsPerPage) {
        List<StatusName> statusNameList = new ArrayList<>();
        List<String> statusList = new ArrayList<>();
        if (status != null) {
            statusNameList.add(StatusName.valueOf(status));
            statusList.add(status);
        } else {
            statusList = Arrays.asList(ApplicationConstants.COMPLETED, ApplicationConstants.IN_PROGRESS, ApplicationConstants.YET_TO_START);
            statusNameList = Arrays.asList(StatusName.YET_TO_START, StatusName.IN_PROGRESS, StatusName.COMPLETED);
        }
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
        UserDetail userDetail = userDetailRepository.findByUserMailId(studentName);
        Student student = studentRepository.findByUserDetailUserMailId(userDetail.getUserMailId());
        Page<Task> taskPage = taskRepository.findByStudentStudentIdAndTaskStatusIdStatusNameIn(student.getStudentId(), statusNameList, pageable);
        List<TaskResponseDTO> taskResponseDTOList = taskPage.stream().map(task -> new TaskResponseDTO("null", task.getTaskId(), task.getTaskName(), task.getTaskDescription(), task.getStudent().getStudentId(), task.getTaskStatusId(), task.getSubject())).collect(Collectors.toList());
        return new ViewTaskResponseDTO(pageNumber, itemsPerPage, taskResponseDTOList, statusList);
    }
}

package com.example.userServiceTaskManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetailsDTO {
    private String studentEmail;
    private String studentName;
    private String studentId;
}

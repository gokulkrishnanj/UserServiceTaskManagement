package com.example.userServiceTaskManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeStatusDTO {
    private String status;
    private String taskName;
    private String subjectName;
}

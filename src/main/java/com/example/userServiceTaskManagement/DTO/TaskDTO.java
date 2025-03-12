package com.example.userServiceTaskManagement.DTO;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    @Size(min = 1, max = 20, message = "Only minimum of 1 and maximum of 20 characters are allowed")
    private String taskName;

    @Size(min = 1, max = 200, message = "Only minimum of 1 and maximum of 200 characters are allowed")
    private String taskDescription;

    private String subject;
}

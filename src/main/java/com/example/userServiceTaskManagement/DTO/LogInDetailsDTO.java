package com.example.userServiceTaskManagement.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInDetailsDTO {
    private String message;
    private String accessToken;
    private String refreshToken;
}

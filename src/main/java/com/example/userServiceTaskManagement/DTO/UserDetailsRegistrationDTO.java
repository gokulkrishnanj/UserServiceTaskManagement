package com.example.userServiceTaskManagement.DTO;

import com.example.userServiceTaskManagement.Entity.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRegistrationDTO {
    private String message;
    private UserDetail userDetail;
}

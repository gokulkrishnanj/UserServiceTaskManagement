package com.example.userServiceTaskManagement.Controller;

import com.example.userServiceTaskManagement.API.UserDetailAPI;
import com.example.userServiceTaskManagement.DTO.LogInDetailsDTO;
import com.example.userServiceTaskManagement.DTO.UserDetailsRegistrationDTO;
import com.example.userServiceTaskManagement.Entity.UserDetail;
import com.example.userServiceTaskManagement.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserDetailController implements UserDetailAPI {

    @Autowired
    private UserDetailService userDetailService;

    public ResponseEntity<UserDetailsRegistrationDTO> registerUser(UserDetail userDetail) {
        return new ResponseEntity<>(userDetailService.registerUser(userDetail), HttpStatus.OK);
    }

    public ResponseEntity<LogInDetailsDTO> logInUser(UserDetail userDetail) {
        return new ResponseEntity<>(userDetailService.logInUser(userDetail), HttpStatus.OK);
    }

    public ResponseEntity<LogInDetailsDTO> refreshNewToken(String refreshToken) {
        return new ResponseEntity<>(userDetailService.refreshNewToken(refreshToken), HttpStatus.OK);
    }
}

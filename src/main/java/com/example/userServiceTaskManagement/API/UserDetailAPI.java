package com.example.userServiceTaskManagement.API;


import com.example.userServiceTaskManagement.DTO.LogInDetailsDTO;
import com.example.userServiceTaskManagement.DTO.UserDetailsRegistrationDTO;
import com.example.userServiceTaskManagement.Entity.UserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "user/api/v1")
public interface UserDetailAPI {

    @PostMapping(value = "/register/user")
    public ResponseEntity<UserDetailsRegistrationDTO> registerUser(@RequestBody  UserDetail userDetail);

    @GetMapping(value = "/login/user")
    public ResponseEntity<LogInDetailsDTO> logInUser(@RequestBody UserDetail userDetail);

    @GetMapping(value = "/refresh")
    public ResponseEntity<LogInDetailsDTO> refreshNewToken(@RequestParam String refreshToken);


}

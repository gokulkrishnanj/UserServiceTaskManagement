package com.example.userServiceTaskManagement.API;


import com.example.userServiceTaskManagement.Entity.UserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("user/api/v1")
public interface UserDetailAPI {

    @PostMapping(value = "/register/User")
    public ResponseEntity<UserDetail> registerUser(UserDetail userDetail);

}

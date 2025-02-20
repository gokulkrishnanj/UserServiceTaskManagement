package com.example.userServiceTaskManagement.API;


import com.example.userServiceTaskManagement.Entity.UserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "user/api/v1")
public interface UserDetailAPI {

    @PostMapping(value = "/register/User")
    public ResponseEntity<UserDetail> registerUser(@RequestBody  UserDetail userDetail);

}

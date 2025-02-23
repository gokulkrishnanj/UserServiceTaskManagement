package com.example.userServiceTaskManagement.Service;

import com.example.userServiceTaskManagement.API.UserDetailAPI;
import com.example.userServiceTaskManagement.DTO.UserDetailsRegistrationDTO;
import com.example.userServiceTaskManagement.Entity.UserDetail;

public interface UserDetailService {

    public UserDetailsRegistrationDTO registerUser(UserDetail userDetail);

    public String logInUser(UserDetail userDetail);

}

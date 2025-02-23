package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.DTO.UserDetailsRegistrationDTO;
import com.example.userServiceTaskManagement.Entity.UserDetail;
import com.example.userServiceTaskManagement.Repository.UserDetailRepository;
import com.example.userServiceTaskManagement.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetailsRegistrationDTO registerUser(UserDetail userDetail){
        UserDetailsRegistrationDTO userDetailsRegistrationDTO = new UserDetailsRegistrationDTO();
        UserDetail userDetails = userDetailRepository.findByUserMailId(userDetail.getUserMailId());
        if(!Objects.isNull(userDetails)){
            userDetailsRegistrationDTO.setMessage("Already registered user");
            userDetail.setUserId(userDetails.getUserId());
            userDetailsRegistrationDTO.setUserDetail(userDetail);
        }
        else{
            userDetailRepository.save(userDetail);
            userDetailsRegistrationDTO.setMessage("user registered");
        }
        return userDetailsRegistrationDTO;
    }

    public String logInUser(UserDetail userDetail){
        UserDetail userDetail1 = userDetailRepository.findByUserMailId(userDetail.getUserMailId());
        System.out.println("="+userDetail1.getUserMailId());
        if(!Objects.isNull(userDetail1))
                return "success";
        return "User not found, please register";
    }

}

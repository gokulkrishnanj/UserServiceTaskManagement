package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.Entity.UserDetail;
import com.example.userServiceTaskManagement.Repository.UserDetailRepository;
import com.example.userServiceTaskManagement.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetail registerUser(UserDetail userDetail){
        userDetailRepository.save(userDetail);
        return userDetail;
    }

}

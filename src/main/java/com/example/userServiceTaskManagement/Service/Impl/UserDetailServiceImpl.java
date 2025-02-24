package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.DTO.UserDetailsRegistrationDTO;
import com.example.userServiceTaskManagement.Entity.UserDetail;
import com.example.userServiceTaskManagement.Repository.UserDetailRepository;
import com.example.userServiceTaskManagement.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    @Override
    public UserDetailsRegistrationDTO registerUser(UserDetail userDetail){
        UserDetailsRegistrationDTO userDetailsRegistrationDTO = new UserDetailsRegistrationDTO();
        UserDetail userDetails = userDetailRepository.findByUserMailId(userDetail.getUserMailId());
        if(!Objects.isNull(userDetails)){
            userDetailsRegistrationDTO.setMessage("Already registered user");
        }
        else{
            userDetail.setPassword(bCryptPasswordEncoder.encode(userDetail.getPassword()));
            userDetailRepository.save(userDetail);
            userDetailsRegistrationDTO.setMessage("user registered");
        }
        return userDetailsRegistrationDTO;
    }

    public String logInUser(UserDetail userDetail){
        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetail.getUserMailId(),userDetail.getPassword()));
        if(authenticate.isAuthenticated())
                return jwtService.generateToken(userDetail);
        return "User not found, please register";
    }

}

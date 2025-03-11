package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.DTO.LogInDetailsDTO;
import com.example.userServiceTaskManagement.DTO.UserDetailsRegistrationDTO;
import com.example.userServiceTaskManagement.Entity.Student;
import com.example.userServiceTaskManagement.Entity.UserDetail;
import com.example.userServiceTaskManagement.Repository.StudentRepository;
import com.example.userServiceTaskManagement.Repository.UserDetailRepository;
import com.example.userServiceTaskManagement.Service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public UserDetailsRegistrationDTO registerUser(UserDetail userDetail) {
        UserDetailsRegistrationDTO userDetailsRegistrationDTO = new UserDetailsRegistrationDTO();
        UserDetail userDetails = userDetailRepository.findByUserMailId(userDetail.getUserMailId());
        if (!Objects.isNull(userDetails)) {
            userDetailsRegistrationDTO.setMessage("Already registered user");
        } else {
            userDetail.setPassword(bCryptPasswordEncoder.encode(userDetail.getPassword()));
            userDetailRepository.save(userDetail);
            if(userDetail.getRole().equals("STUDENT")){
                Student student = new Student();
                student.setUserDetail(userDetail);
                studentRepository.save(student);
            }
            userDetailsRegistrationDTO.setMessage("user registered");
        }
        return userDetailsRegistrationDTO;
    }

    public LogInDetailsDTO logInUser(UserDetail userDetail) {
        LogInDetailsDTO logInDetailsDTO = new LogInDetailsDTO();
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetail.getUserMailId(), userDetail.getPassword()));
        if (authenticate.isAuthenticated()) {
            log.info("auth user-------");
            UserDetails userDetails = userDetailsService.loadUserByUsername(userDetail.getUserMailId());
            String accessToken = "Bearer " + jwtService.generateToken(userDetails);
            String refreshToken = "Bearer " + jwtService.generateRefreshToken(userDetails);
            logInDetailsDTO.setAccessToken(accessToken);
            logInDetailsDTO.setRefreshToken(refreshToken);
            logInDetailsDTO.setMessage("Login Successful");
            return logInDetailsDTO;
        }
        return logInDetailsDTO;
    }

    @Override
    public LogInDetailsDTO refreshNewToken(String refreshToken) {
        LogInDetailsDTO logInDetailsDTO = new LogInDetailsDTO();
        System.out.println("refToken:" + refreshToken);
        String userName = jwtService.extractUserNameFromToken(refreshToken.substring(7).trim());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        boolean isExpired = jwtService.isTokenValid(refreshToken.substring(7).trim(), userDetails);
        if (isExpired) {
            String accessToken = "Bearer " + jwtService.generateToken(userDetails);
            refreshToken = "Bearer " + jwtService.generateRefreshToken(userDetails);
            logInDetailsDTO.setRefreshToken(refreshToken);
            logInDetailsDTO.setAccessToken(accessToken);
            logInDetailsDTO.setMessage("new token generated");
            return logInDetailsDTO;
        }
        logInDetailsDTO.setMessage("Refresh token expired");
        return logInDetailsDTO;
    }

}

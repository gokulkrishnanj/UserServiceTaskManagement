package com.example.userServiceTaskManagement.Service.Impl;

import com.example.userServiceTaskManagement.DTO.LogInDetailsDTO;
import com.example.userServiceTaskManagement.DTO.UserDetailsRegistrationDTO;
import com.example.userServiceTaskManagement.Entity.UserDetail;
import com.example.userServiceTaskManagement.Repository.UserDetailRepository;
import com.example.userServiceTaskManagement.Service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private UserDetailsService userDetailsService;

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

    public LogInDetailsDTO logInUser(UserDetail userDetail){
        LogInDetailsDTO logInDetailsDTO = new LogInDetailsDTO();
        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetail.getUserMailId(),userDetail.getPassword()));
        if(authenticate.isAuthenticated()){
            String accessToken = jwtService.generateToken(userDetail.getUserMailId());
            String refreshToken = jwtService.generateRefreshToken(userDetail.getUserMailId());
            logInDetailsDTO.setAccessToken(accessToken);
            logInDetailsDTO.setRefreshToken(refreshToken);
            logInDetailsDTO.setMessage("Login Successful");
            return logInDetailsDTO;
        }
        logInDetailsDTO.setMessage("User not found");
        return logInDetailsDTO;
    }

    @Override
    public LogInDetailsDTO refreshNewToken(String refreshToken){
        LogInDetailsDTO logInDetailsDTO = new LogInDetailsDTO();
        String userName = jwtService.extractUserNameFromToken(refreshToken.trim());
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        boolean isExpired = jwtService.isTokenValid(refreshToken.trim(),userDetails);
        if(isExpired){
            String accessToken = jwtService.generateToken(userDetails.getUsername());
            logInDetailsDTO.setRefreshToken(refreshToken);
            logInDetailsDTO.setAccessToken(accessToken);
            logInDetailsDTO.setMessage("new token generated");
            return logInDetailsDTO;
        }
        logInDetailsDTO.setMessage("Refresh token expired");
        return logInDetailsDTO;
    }

}

package com.example.userServiceTaskManagement.Configuration;

import com.example.userServiceTaskManagement.Entity.UserDetail;
import com.example.userServiceTaskManagement.Repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private CustomUserDetail customUserDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail = userDetailRepository.findByUserMailId(username);
        if(Objects.isNull(username)){
            throw new UsernameNotFoundException("User is not registered, Please register to continue");
        }
        return new CustomUserDetail(userDetail);
    }
}

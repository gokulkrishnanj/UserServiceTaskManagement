package com.example.userServiceTaskManagement.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;


@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        List<String> whiteListAPIs = List.of("/**/register/**","/**/login/**", "/**/Login/**", "**/Register/**");

        httpSecurity
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {whiteListAPIs.forEach(api-> authorizationManagerRequestMatcherRegistry.requestMatchers(new AntPathRequestMatcher(api)).permitAll());
                authorizationManagerRequestMatcherRegistry.anyRequest().authenticated();})
                .httpBasic(Customizer.withDefaults())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        return httpSecurity.build();
    }

    public UserDetailsService userDetailsService() throws Exception{
        UserDetails user = User
                .withUsername("user")
                .password("{noop}user")
                .roles("STUDENT")
                .build();
        UserDetails user1 = User
                .withUsername("user1")
                .password("{noop}user1")
                .roles("STUDENT")
                .build();
        return new InMemoryUserDetailsManager(user,user1);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authenticationProvider;
    }

}

package com.example.userServiceTaskManagement.Configuration;

import com.example.userServiceTaskManagement.Exceptions.CustomAccessDeniedExceptionHandler;
import com.example.userServiceTaskManagement.Exceptions.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.List;


@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthneticationEntryPoint;

    @Autowired
    private CustomAccessDeniedExceptionHandler customAccessDeniedExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        List<String> whiteListAPIs = List.of("/**/register/**","/**/login/**", "/**/Login/**", "**/Register/**", "/**/refresh**");

        httpSecurity
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {whiteListAPIs.forEach(api-> authorizationManagerRequestMatcherRegistry.requestMatchers(new AntPathRequestMatcher(api)).permitAll());
                authorizationManagerRequestMatcherRegistry.requestMatchers(request -> request.getRequestURI().contains("/student")).hasRole("STUDENT").requestMatchers(request -> request.getRequestURI().contains("teacher")).hasRole("TEACHER").anyRequest().authenticated();})
                .httpBasic(Customizer.withDefaults())
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(customAccessDeniedExceptionHandler).authenticationEntryPoint(customAuthneticationEntryPoint))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    // loading from inmemory
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
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(16);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

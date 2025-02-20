package com.example.userServiceTaskManagement.API;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "api/v1")
public interface HealthAPI {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<CsrfToken> getHealth(HttpServletRequest request);

    @PostMapping(value = "/health")
    public ResponseEntity<String> postHealth();

}

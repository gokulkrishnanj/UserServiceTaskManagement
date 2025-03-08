package com.example.userServiceTaskManagement.API;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = "api/v1")
public interface HealthAPI {

    @RequestMapping(value = "/teacher/health", method = RequestMethod.GET)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CsrfToken> getHealth(HttpServletRequest request);

    @PostMapping(value = "/student/health")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> postHealth();

}

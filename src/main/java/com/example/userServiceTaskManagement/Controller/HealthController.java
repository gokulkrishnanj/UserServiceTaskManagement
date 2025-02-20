package com.example.userServiceTaskManagement.Controller;


import com.example.userServiceTaskManagement.API.HealthAPI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController implements HealthAPI {

    public ResponseEntity<CsrfToken> getHealth(HttpServletRequest request){
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return new ResponseEntity<>(csrfToken, HttpStatus.OK);
    }
    public ResponseEntity<String> postHealth(){
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}

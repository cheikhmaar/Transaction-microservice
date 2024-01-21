package com.uahb.m1info.api.gatewaye.controller;



import com.uahb.m1info.api.gatewaye.model.LoginRequest;
import com.uahb.m1info.api.gatewaye.model.LoginResponse;
import com.uahb.m1info.api.gatewaye.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/users/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest loginRequest)
            throws Exception {
        log.info("Executing login");

        ResponseEntity<LoginResponse> response = loginService.login(loginRequest);

        return response;
    }
}

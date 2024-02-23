package com.nbh.cafe.service;

import com.nbh.cafe.dto.LoginRequest;
import com.nbh.cafe.dto.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> signup(SignupRequest request);
    boolean validateSignupRequest(SignupRequest request);

    ResponseEntity<?> login(LoginRequest request);
}

package com.nbh.cafe.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    //UserDetailsService userDetailsService();
    ResponseEntity<?> getAllUsers();
}

package com.nbh.cafe.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String getUsername(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateRefreshToken(UserDetails userDetails);
    boolean isTokenExpired(String token);
}

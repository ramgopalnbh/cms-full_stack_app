package com.nbh.cafe.dto;

import com.nbh.cafe.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String refreshToken;
    private UserRole userRole;
    private Integer userId;
}

package com.nbh.cafe.dto;

import com.nbh.cafe.enums.UserRole;
import lombok.Data;

@Data
public class SignupRequest {
    private String name;
    private String email;
    private String password;
}

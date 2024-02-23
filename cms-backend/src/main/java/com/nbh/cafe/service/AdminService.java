package com.nbh.cafe.service;

import com.nbh.cafe.dto.CategoryRequest;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<?> addCategory(CategoryRequest request);
}

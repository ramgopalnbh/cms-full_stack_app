package com.nbh.cafe.controller;

import com.nbh.cafe.constants.CafeConstant;
import com.nbh.cafe.dto.CategoryRequest;
import com.nbh.cafe.service.AdminService;
import com.nbh.cafe.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody CategoryRequest request){
        try{
            log.info("Inside add Category by admin controller");
            return adminService.addCategory(request);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getData(){
        log.info("Inside get by admin controller");
        Map<String,String> data = new HashMap<>();
        data.put("name","Ram Gopal");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}

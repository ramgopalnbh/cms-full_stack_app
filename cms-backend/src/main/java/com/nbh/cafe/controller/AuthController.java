package com.nbh.cafe.controller;

import com.nbh.cafe.constants.CafeConstant;
import com.nbh.cafe.dto.LoginRequest;
import com.nbh.cafe.dto.SignupRequest;
import com.nbh.cafe.service.AuthenticationService;
import com.nbh.cafe.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationService authService;
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request){
        try{
            return authService.signup(request);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try{
            return authService.login(request);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/get")
    public ResponseEntity<?> getData(){
        Map<String,String> map = new HashMap<>();
        map.put("application_name","CMS");
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}

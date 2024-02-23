package com.nbh.cafe.service.serviceImpl;

import com.nbh.cafe.constants.CafeConstant;
import com.nbh.cafe.dto.LoginRequest;
import com.nbh.cafe.dto.LoginResponse;
import com.nbh.cafe.dto.SignupRequest;
import com.nbh.cafe.enums.UserRole;
import com.nbh.cafe.model.User;
import com.nbh.cafe.repository.UserRepository;
import com.nbh.cafe.service.AuthenticationService;
import com.nbh.cafe.service.JwtService;
import com.nbh.cafe.utils.CafeUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtService jwtService;
    @PostConstruct
    private void createAdminAccount(){
        try{
            log.info("Inside createAdminAccount..");
            Optional<User> existingUser = userRepository.findUserByUserRole(UserRole.ADMIN);
            if(!existingUser.isPresent()){
                User user = new User();
                user.setName("admin");
                user.setEmail("admin@gmail.com");
                user.setPassword(passwordEncoder.encode("admin@123"));
                user.setUserRole(UserRole.ADMIN);
                userRepository.save(user);
                log.info("Admin account created successfully.");
            }else{
                log.info("Admin account exists.");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    @Override
    public ResponseEntity<?> signup(SignupRequest request) {
        try{
            Optional<User> user = userRepository.findUserByEmail(request.getEmail());
            if(validateSignupRequest(request)){
                log.info("Request data is valid, going to create user...");
                if(user.isPresent()){
                    log.info("This email already exists!");
                    return new ResponseEntity<>("This email already exists!", HttpStatus.BAD_REQUEST);
                }else{
                    User user1 = new User();
                    user1.setName(request.getName());
                    user1.setEmail(request.getEmail());
                    user1.setPassword(passwordEncoder.encode(request.getPassword()));
                    user1.setUserRole(UserRole.CUSTOMER);
                    userRepository.save(user1);
                    log.info("User registered successfully.");
                    return new ResponseEntity<>("User registered successfully.",HttpStatus.OK);
                }
            }else{
                log.info("Request data is invalid!!");
                return CafeUtils.getResponseEntity(CafeConstant.INVALID_DATA,HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstant.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public boolean validateSignupRequest(SignupRequest request) {
        return (request.getName() != null &&
                request.getEmail() != null &&
                request.getPassword() != null
               );
    }

    @Override
    public ResponseEntity<?> login(LoginRequest request) {
        try{
            log.info("Inside login method");
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            Optional<User> user = userRepository.findUserByEmail(userDetails.getUsername());
            if(user.isPresent()){
                String jwtToken = jwtService.generateToken(userDetails);
                String jwtRefreshToken = jwtService.generateRefreshToken(userDetails);
                LoginResponse response = LoginResponse.builder()
                        .token(jwtToken)
                        .refreshToken(jwtRefreshToken)
                        .userRole(user.get().getUserRole())
                        .userId(user.get().getId())
                        .build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                return CafeUtils.getResponseEntity("User does not exist!!",HttpStatus.BAD_REQUEST);
            }

        }catch (BadCredentialsException ex){
            throw new BadCredentialsException("Incorrect Username or Password!!");
        }
    }
}

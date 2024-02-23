package com.nbh.cafe.service.serviceImpl;

import com.nbh.cafe.repository.UserRepository;
import com.nbh.cafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository repository;
//    @Override
//    public UserDetailsService userDetailsService() {
//        return new UserDetailsService() {
//            @Override
//            public UserDetails loadUserByUsername(String username) {
//                return (UserDetails) repository.findUserByEmail(username)
//                        .orElseThrow(()-> new UsernameNotFoundException("User Not Found!!"));
//            }
//        };
//    }
    @Override
    public ResponseEntity<?> getAllUsers() {
        return null;
    }
}

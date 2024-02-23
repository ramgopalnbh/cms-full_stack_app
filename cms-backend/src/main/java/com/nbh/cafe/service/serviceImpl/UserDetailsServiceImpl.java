package com.nbh.cafe.service.serviceImpl;

import com.nbh.cafe.model.User;
import com.nbh.cafe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            Optional<User> user = userRepository.findUserByEmail(username);
            if(user.isPresent()){
                return new org.springframework.security.core.userdetails.User(user.get().getEmail(),user.get().getPassword(),new ArrayList<>());
            }
        }catch (UsernameNotFoundException ex){
            throw new UsernameNotFoundException("User not found!!");
        }
        return null;
    }
}

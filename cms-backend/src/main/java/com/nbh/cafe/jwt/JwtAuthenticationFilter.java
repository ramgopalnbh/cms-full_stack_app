package com.nbh.cafe.jwt;

import com.nbh.cafe.service.JwtService;
import com.nbh.cafe.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserDetailsService userDetailsService;
    String userEmail =null;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader =request.getHeader("Authorization");
        log.info("Authorization:--- "+authHeader);
        String token = null;
        userEmail = null;
        if(authHeader !=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userEmail = jwtService.getUsername(token);
        }
        if(userEmail !=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if(jwtService.isTokenValid(token,userDetails)){
                log.info("Token is valid, go ahead..");
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);
            } else
                log.info("token validation failed!!");
        }
        filterChain.doFilter(request,response);
    }
    public String getCurrentUser(){
        return userEmail;
    }
}

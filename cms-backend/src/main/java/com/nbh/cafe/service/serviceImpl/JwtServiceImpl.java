package com.nbh.cafe.service.serviceImpl;

import com.nbh.cafe.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private static final String secretKey = "Rgv123sj5dfh234vkd5nb5vdh234534nss5d54321jsbdfadfbfjhsbfebsddvsbfsdfsfksjgjsgsvnknjks";
    String username = null;
    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Claims claims =null;
        try{
            claims = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        }catch (JwtException ex){
            log.info("Facing problem on extracting all claims..May be session expired");
            ex.printStackTrace();
        }
        return claims;
    }
    @Override
    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    private SecretKey getSigningKey(){
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
    @Override
    public String generateToken(UserDetails userDetails) {
        String token =null;
        username =userDetails.getUsername();

        token =  Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        if(token.isEmpty())
            log.info("token not generated..");
        else {
//            LoginMonitor loginMonitor = new LoginMonitor();
//            loginMonitor.setLoginTime(new Date(System.currentTimeMillis()));
//            loginMonitor.setToken(token);
//            monitorRepository.save(loginMonitor);
            log.info("token generated successfully.");
        }
        return token;
    }
    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return null;
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    @Override
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}

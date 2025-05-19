package com.anas.springblog.utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10;
    private final String SECRET_KEY = "your-key-keep-it-safe123456789@secret";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return getPayload(token).getSubject();
    }

    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean validate(String username, UserDetails userDetails, String jwtToken) {
        return username.equals(userDetails.getUsername()) && !isExpired(jwtToken);
    }

    private boolean isExpired(String jwtToken) {
        return getPayload(jwtToken).getExpiration().before(new Date());
    }
}

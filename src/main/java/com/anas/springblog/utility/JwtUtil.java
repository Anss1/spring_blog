package com.anas.springblog.utility;

import com.anas.springblog.model.User;
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
    private static final String SECRET_KEY = "your-key-keep-it-safe123456789@secret";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(User user){
        return Jwts.builder()
                .claim("id",user.getId())
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return getPayload(token).getSubject();
    }
    public Long extractUserId(String token) {
        return getPayload(token).get("id",Long.class);
    }

    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean validate(UserDetails userDetails, String token) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        return getPayload(token).getExpiration().before(new Date());
    }


}

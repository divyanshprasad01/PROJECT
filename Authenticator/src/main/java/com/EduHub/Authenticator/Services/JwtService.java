package com.EduHub.Authenticator.Services;

import com.EduHub.Authenticator.Models.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final String SECRET_KEY = "secretKeyChangeWithYourOwnSecretKey";


    public String generateNewToken(Users user) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 60 * 60 * 60 * 24))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsernameFromToken(String token) {
        return extractClaim(token, "sub", String.class);
    }

    public boolean validateToken(String token, Users user) {
        return (extractUsernameFromToken(token).equals(user.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date());
    }

    public Date extractExpirationTime(String token){
        return extractClaim(token, "exp", Date.class);
    }

    public <T> T extractClaim(String token, String claimName, Class<T> claimClass){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimName, claimClass);
    }
}

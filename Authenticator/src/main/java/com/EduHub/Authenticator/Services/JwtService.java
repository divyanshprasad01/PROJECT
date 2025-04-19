package com.EduHub.Authenticator.Services;

import com.EduHub.Authenticator.Models.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//A Json Web Token service to generate new tokens and validate old tokens...
@Service
public class JwtService {

//  this is the secret signing key, json tokens are signed with this key, this must be randomly generated automatically when we build
//    the application for now I hardaCoded it.
    private final String SECRET_KEY = "secretKeyChangeWithYourOwnSecretKey";

/*  Claims in JSON Web Tokens (JWTs) are key-value pairs that convey specific information about the subject or entity the
token represents. They are the core payload of the token and contain data that can be used by the receiver for authentication,
authorization, and other purposes.
JWT claims are divided into three categories:
1. Registered Claims: These are standardized claims defined by the JWT specification to provide common metadata. Examples include:
   - `iss` (Issuer): Identifies who issued the token.
   - `sub` (Subject): Represents the principal subject of the token.
   - `aud` (Audience): Specifies the intended audience of the token.
   - `exp` (Expiration Time): Indicates when the token expires.
   - `nbf` (Not Before): Specifies when the token becomes valid.
   - `iat` (Issued At): Denotes when the token was issued.
*/

//Generates a new json web token..
    public String generateNewToken(Users user) {
//        In these you can add your custom claims but we dont need any so we are just passing it empty..
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
//                It takes a map of String and Objects which adds the custom claims if required..
                .setClaims(claims)
//                It sets the username in the token...
                .setSubject(user.getUsername())
//                This takes the current system time and sets it as the issue time of the token...
                .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
//                It sets the expiration time of the token..like this
//                Takes current time + 60 miliseconds * 60 seconds * 60 minutes * 24 hours = 24 hours after the current time, the token will expire..
                .setExpiration(new java.util.Date(System.currentTimeMillis() + 60 * 60 * 60 * 24))
//                It signs the token with the seceret siging key...
                .signWith(getSigningKey())
//                It is used to generate the final string with all the header payload and signature..
                .compact();
    }

//    It returns a key Object which takes a string in bytes array.
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

//    Extracts the username from the token, refers above for explanation of claims.
    public String extractUsernameFromToken(String token) {
        return extractClaim(token, "sub", String.class);
    }

//    Validates token checks if token belongs to user fetched from database and also checks if token is not expired.
    public boolean validateToken(String token, Users user) {
        return (extractUsernameFromToken(token).equals(user.getUsername()) && !isTokenExpired(token));
    }

//    checks weather token expiration time is after the current system time.
    public boolean isTokenExpired(String token) {
        return extractExpirationTime(token).before(new Date());
    }

//    Extracts the expiration time from the token, refer above for claims explanation.
    public Date extractExpirationTime(String token){
        return extractClaim(token, "exp", Date.class);
    }

//    extracts the claim fields from the given token with claim name and their types.
    public <T> T extractClaim(String token, String claimName, Class<T> claimClass){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get(claimName, claimClass);
    }
}

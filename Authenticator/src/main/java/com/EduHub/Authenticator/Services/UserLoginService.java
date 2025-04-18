package com.EduHub.Authenticator.Services;


import com.EduHub.Authenticator.Models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    public String authenticateUser(Users user) {
        Authentication auth =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        return (auth.isAuthenticated())? jwtService.generateNewToken(user) :"failed";
    }
}

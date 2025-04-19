package com.EduHub.Authenticator.Services;


import com.EduHub.Authenticator.Models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

//This is a user login service here it takes username and password to authenticate user,
//users only hit this service when json web token is expired or not available in the first place.
@Service
public class UserLoginService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

//   It authenticates the user with username and password with the help of authentication manager and
//   then generates a new token if user is authenticated
//   and returns it to user and if not it shows faild.
    public String authenticateUser(Users user) {
        Authentication auth =
                authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        return (auth.isAuthenticated())? jwtService.generateNewToken(user) :"failed";
    }
}

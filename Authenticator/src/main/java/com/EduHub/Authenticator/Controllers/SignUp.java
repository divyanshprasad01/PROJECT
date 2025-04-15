package com.EduHub.Authenticator.Controllers;

import com.EduHub.Authenticator.Models.Users;
import com.EduHub.Authenticator.Services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


//A rest controller for sign up page...currently there is no page but working fine when testing in POSTMAN...
@RestController
public class SignUp {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signup")
    public Users signUp(@RequestBody Users users){
        return signUpService.signUpAndSave(users);
    }

}

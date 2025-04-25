package com.EduHub.Authenticator.Controllers;

import com.EduHub.Authenticator.Models.Users;
import com.EduHub.Authenticator.Services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


//Changed rest controller to controller so that it can serve pages with the help of thymeleaf...
@RestController
public class HomePage {

    @Autowired
    UserLoginService userLoginService;

//    A demo Homepage user sees after logging in.
    @GetMapping("")
    public String homepageDemo() {
        return "Hello World!!";
    }

//    login endpoint where user sends his username and password to login and gets a web token in return.
//    Users should only hit this endpoint when json Web token is expired or not issued in the first place.
    @PostMapping("login")
    public String login(@RequestBody Users user) {
        return userLoginService.authenticateUser(user);
    }


//    Endpoint for Oauth Google Client....
    @PostMapping("loginWithGoogle")
    public String loginWithGoogle(@RequestParam String string){

        return null;
    }

//    Just for testing the signup service this is to get the body of user in json format.
    @GetMapping("/format")
    public String format(Users users) {
        return users.toString();
    }

}

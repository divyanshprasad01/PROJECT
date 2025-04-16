package com.EduHub.Authenticator.Controllers;

import com.EduHub.Authenticator.Models.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


//Changed rest controller to controller so that it can serve pages with the help of thymeleaf...
@Controller
public class HomePage {

//    A demo Homepage user sees after logging in.
    @GetMapping("home")
    public String homepageDemo() {
        return "home";
    }

//    custom-login form which is served when users hits the site...named it custom-login because
//    login was giving some error and bugs....
    @GetMapping("custom-login")
    public String login(){
        return "Mylogin";
    }

//    Just for testing the signup service this is to get the body of user in json format.
    @GetMapping("format")
    public String format(Users users) {
        return users.toString();
    }

}

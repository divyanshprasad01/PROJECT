package com.EduHub.Authenticator.Controllers;

import com.EduHub.Authenticator.Models.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


//Changed rest controller to controller so that it can serve pages with the help of thymeleaf...
@RestController
public class HomePage {

//    A demo Homepage user sees after logging in.
    @GetMapping("/")
    public String homepageDemo() {
        return "Hello World!!";
    }

//    Just for testing the signup service this is to get the body of user in json format.
    @GetMapping("/format")
    public String format(Users users) {
        return users.toString();
    }

}

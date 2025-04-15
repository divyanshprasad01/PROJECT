package com.EduHub.Authenticator.Controllers;

import com.EduHub.Authenticator.Models.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

//    A demo Homepage user sees after logging in.
    @GetMapping("/")
    public String homepageDemo() {
        return "Hello world!!";
    }

//    Just for testing the signup service this is to get the body of user in json format.
    @GetMapping("/format")
    public String format(Users users) {
        return users.toString();
    }

}

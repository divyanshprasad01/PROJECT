package com.EduHub.Authenticator.Controllers;

import com.EduHub.Authenticator.Models.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/")
    public String homepageDemo() {
        return "Hello world!!";
    }

    @GetMapping("/format")
    public String format(Users users) {
        return users.toString();
    }

}

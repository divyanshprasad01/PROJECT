package com.EduHub.Authenticator.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/")
    public String homepageDemo() {
        return "Hello world!!";
    }

}

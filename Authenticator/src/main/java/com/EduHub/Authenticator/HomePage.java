package com.EduHub.Authenticator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomePage {

    @GetMapping("/homepage")
    public String homepageDemo() {
        return "Hello world!!";
    }

}

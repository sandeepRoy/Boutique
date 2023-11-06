package com.boutique.controllers;

import jakarta.annotation.security.RolesAllowed;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")

public class UserProfileController {
    @GetMapping(value = "/profile/update")
    public String profileUpdate(){
        return "Comming Soon!!";
    }
}

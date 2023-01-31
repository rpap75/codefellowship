package com.rpap.codefellowship.controllers;

import com.rpap.codefellowship.models.SiteUser;
import com.rpap.codefellowship.repositories.SiteUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.HttpServletBean;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@Controller
public class SiteUserController {

@Autowired
    SiteUserRepository siteUserRepository;

@Autowired
    PasswordEncoder passwordEncoder;

@Autowired
    HttpServletRequest request;

    @PostMapping("/register")
    public RedirectView createSiteUser(String username, String password, String firstName, String lastName, Date dateOfBirth, String bio){
        String hashedPW = passwordEncoder.encode(password);
        SiteUser newUser = new SiteUser(username, hashedPW, firstName, lastName, new Date(), bio);
        siteUserRepository.save(newUser);
        return new RedirectView("/login");
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login.html";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register.html";
    }

    @GetMapping("/")
    public String getHome(Model m, Principal p){
if (p != null) {
    String username = p.getName();
    SiteUser dbUser = siteUserRepository.findByUsername(username);

    m.addAttribute("username", username);
    m.addAttribute("FirstName", dbUser.getFirstName());
}

        return "index.html";
    }

    @GetMapping("/application")
    public String getApplication () {
        return "ApplicationUser.html";
    }
}

package com.rpap.codefellowship.controllers;

import com.rpap.codefellowship.models.SiteUser;
import com.rpap.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class SiteUserController {

    @Autowired
    SiteUserRepository siteUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register.html";
    }

    @GetMapping("/application")
    public String getApplication() {
        return "ApplicationUser.html";
    }

    @GetMapping("/")
    public String getHome(Model m, Principal p) {
        if (p != null) {
            String username = p.getName();
            SiteUser dbUser = siteUserRepository.findByUsername(username);

            m.addAttribute("username", username);
            m.addAttribute("FirstName", dbUser.getFirstName());
        }
        try {

        } catch (RuntimeException runtimeException) {
            throw new RuntimeException("OOOOOOOOPPPPPSSSS!");
        }
        return "index.html";
    }

    @GetMapping("/user/{id}")
    public String getOneSiteUser(@PathVariable Long id, Model m, Principal p) {
        SiteUser authenticateUser = siteUserRepository.findByUsername(p.getName());
        String authUserName = authenticateUser.getUsername();
        m.addAttribute("authUserName", authUserName);
        SiteUser viewUser = siteUserRepository.findById(id).orElseThrow();
        Long viewUserId = viewUser.getId();
        String viewUserName = viewUser.getUsername();
        String viewUserFirstName = viewUser.getFirstName();
        m.addAttribute("viewUserFirstName", viewUserFirstName);
        m.addAttribute("viewUserName", viewUserName);
        m.addAttribute("viewUserId", viewUserId);
        m.addAttribute("usersIFollow", viewUser.getUsersIFollow());
        m.addAttribute("usersWhoFollowMe", viewUser.getUsersWhoFollowMe());

//        String getAllUsers = siteUserRepository.findAll();

        return "user-info.html";
    }

    @PostMapping("/register")
    public RedirectView createSiteUser(String username, String password, String firstName, String lastName,
                                       String dateOfBirth, String bio) throws ParseException {
        Date dateExtractor = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        String hashedPW = passwordEncoder.encode(password);
        SiteUser newUser = new SiteUser(username, hashedPW, firstName, lastName, dateExtractor, bio);
        siteUserRepository.save(newUser);
        autoAuthWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    @PutMapping("/user/{id}")
    public RedirectView editSiteUserInfo(@PathVariable Long id, String username, String firstName, Principal p, RedirectAttributes redir) throws ServletException {
        SiteUser userToBeEdited = siteUserRepository.findById(id).orElseThrow();
        if (p.getName().equals(userToBeEdited.getUsername())) {
            userToBeEdited.setUsername(username);
            userToBeEdited.setFirstName(firstName);
            siteUserRepository.save(userToBeEdited);
            request.logout();
            autoAuthWithHttpServletRequest(username, userToBeEdited.getPassword());
        } else {
            redir.addFlashAttribute("errorMessage", "Cannot edit another users info");
        }
        return new RedirectView("/user/" + id);
    }

    @PutMapping("/follow-user/{id}")
    public RedirectView followUser(Principal p, @PathVariable Long id) throws IllegalAccessException {
        SiteUser userToFollow = siteUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Error Reading User From The Database with ID of: " + id));
        SiteUser browsingUser = siteUserRepository.findByUsername(p.getName());
        if (browsingUser.getUsername().equals(userToFollow.getUsername())) {
            throw new IllegalAccessException("Don't Follow Yourself!");
        }

        browsingUser.getUsersIFollow().add(userToFollow);

        siteUserRepository.save(browsingUser);

        return new RedirectView("/user/" + id);
    }

    public void autoAuthWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException se) {
            se.printStackTrace();
        }
    }
}

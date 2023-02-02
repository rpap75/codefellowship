package com.rpap.codefellowship.controllers;

import com.rpap.codefellowship.repositories.PostRepository;
import com.rpap.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {
    @Autowired
    PostRepository postRepository;

    @Autowired
    SiteUserRepository siteUserRepository;

    //Get

    //post
}

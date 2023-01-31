package com.rpap.codefellowship.configs;

import com.rpap.codefellowship.repositories.SiteUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SiteUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    SiteUserRepository siteUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("SITE USER DETAILS SERVICE CALLS FOR USER FROM DATABASE");
        return siteUserRepository.findByUsername(username);
    }
}

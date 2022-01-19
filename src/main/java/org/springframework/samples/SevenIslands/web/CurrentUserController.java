package org.springframework.samples.SevenIslands.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CurrentUserController {

    @GetMapping("/currentuser")
    public String showCurrentUser(){
        Authentication authetication = SecurityContextHolder.getContext().getAuthentication();
        if(authetication != null)
            if(authetication.isAuthenticated()){
                User currentUser = (User)authetication.getPrincipal();
                log.info("User authenticated: {}",currentUser.getUsername());
    }else
                log.info("user not authenticated");
        return "/welcome";
    }
    
}

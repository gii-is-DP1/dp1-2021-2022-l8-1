package org.springframework.samples.SevenIslands.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CurrentUserController {

    @GetMapping("/currentuser")
    public String showCurrentUser(){
        Authentication authetication = SecurityContextHolder.getContext().getAuthentication();
        if(authetication != null)
            if(authetication.isAuthenticated()){
                User currentUser = (User)authetication.getPrincipal();
                System.out.println(currentUser.getUsername());
    }else
                System.out.println("user not authenticated");
        return "/welcome";
    }
    
}

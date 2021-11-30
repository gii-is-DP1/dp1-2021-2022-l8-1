package org.springframework.samples.SevenIslands.util;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.web.WelcomeController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;

public class SecurityService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private WelcomeController welcomeController;

    @Transactional
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    @Transactional
    public User getCurrentUser() {
        return (User) getAuthentication().getPrincipal();
    }

    @Transactional
    public int getCurrentUserId() {
        return playerService.getIdPlayerByName(getCurrentUser().getUsername());
    }

    @Transactional
    public Player getCurrentPlayer() {
        return playerService.getPlayerByUsername(getCurrentUser().getUsername()).stream().findFirst().get();
    }

    @Transactional
    public boolean isAdmin() {
        return getAuthentication().getAuthorities().stream().anyMatch(x -> x.toString().equals("admin"));
    }

    @Transactional
    public boolean isAuthenticatedUser() {
        Authentication authentication = getAuthentication();
        if(authentication != null) {
            return authentication.isAuthenticated() && authentication.getPrincipal() instanceof User;
        }   
        return false;
    }

    @Transactional
    public boolean authenticationNotNull() {
        Authentication authentication = getAuthentication();
        return authentication != null;
    }

    @Transactional
    public void insertIdUser(Map<String, Object> model){
		
		Authentication authentication = getAuthentication();
		if (authentication.isAuthenticated()) {
            
            if (isAdmin()) {
                User currentUser = (User) authentication.getPrincipal();
                int playerLoggedId = adminService.getIdAdminByName(currentUser.getUsername());
                model.put("id",playerLoggedId);
                
            } else {
                User currentUser = (User) authentication.getPrincipal();
                int playerLoggedId = playerService.getIdPlayerByName(currentUser.getUsername());
                model.put("id",playerLoggedId);
            }
        }
		
	}

    @Transactional
    public void insertIdUserModelMap(ModelMap model){ 
		
		Authentication authentication = getAuthentication();
		
        if (authentication.isAuthenticated()){
            User currentUser = (User) authentication.getPrincipal();
            int playerLoggedId;

            if (isAdmin()) {
                playerLoggedId = adminService.getIdAdminByName(currentUser.getUsername());
            
            } else {                
                playerLoggedId = playerService.getIdPlayerByName(currentUser.getUsername());
                
            }
            model.put("id",playerLoggedId);

        }
        
      
		
	}

    

}
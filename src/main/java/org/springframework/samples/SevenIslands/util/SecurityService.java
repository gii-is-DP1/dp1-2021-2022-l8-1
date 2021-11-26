package org.springframework.samples.SevenIslands.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;

public class SecurityService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PlayerService playerService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public User getCurrentUser() {
        return (User) getAuthentication().getPrincipal();
    }

    public int getCurrentUserId() {
        return playerService.getIdPlayerByName(getCurrentUser().getUsername());
    }

    public Player getCurrentPlayer() {
        return playerService.getPlayerByUsername(getCurrentUser().getUsername()).stream().findFirst().get();
    }


    public boolean isAdmin() {
        return getAuthentication().getAuthorities().stream().anyMatch(x -> x.toString().equals("admin"));
    }

    public boolean isAuthenticatedUser() {
        Authentication authentication = getAuthentication();
        if(authentication != null) {
            return authentication.isAuthenticated() && authentication.getPrincipal() instanceof User;
        }   
        return false;
    }

    public boolean authenticationNotNull() {
        Authentication authentication = getAuthentication();
        return authentication != null;
    }

    

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

    public void insertIdUserModelMap(ModelMap model){
		
		Authentication authentication = getAuthentication();
		
        if (authentication.isAuthenticated()){
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

    

}
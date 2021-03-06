package org.springframework.samples.SevenIslands.util;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class SecurityService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PlayerService playerService;


    @Transactional
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    @Transactional
    public User getCurrentUser() {
        return (User) getAuthentication().getPrincipal();
    }

    @Transactional
    public int getCurrentPlayerId() {
        return playerService.getIdPlayerByName(getCurrentUser().getUsername());
    }

    @Transactional
    public Player getCurrentPlayer() {
        Optional<Player> opt = playerService.getPlayerByUsername(getCurrentUser().getUsername()).stream().findFirst();
        return opt.isPresent()? opt.get() : null;
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
        if (authentication.getPrincipal() != "anonymousUser"  && authentication.isAuthenticated()){
                
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
		
        if (authentication.getPrincipal() != "anonymousUser" &&  authentication.isAuthenticated()){
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
    public String redirectToWelcome(HttpServletRequest request) {      
        request.getSession().setAttribute("message", "Please, first sign in!");
        return "redirect:/welcome";
    }

    

}
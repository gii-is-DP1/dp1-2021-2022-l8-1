package org.springframework.samples.SevenIslands.general;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {
    @Autowired
    private GeneralRepository generalRepo;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private AdminService adminService;

    @Transactional
    public int generalCount(){
        return (int) generalRepo.count();
    }

    @Transactional
    public int totalDurationAll(){
        return generalRepo.totalDurationAllGames();
    }

    @Transactional
    public int totalGamesCount(){
        return generalRepo.totalGames();
    }

    // public void insertIdUser(Map<String, Object> model){
		
	// 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// 	System.out.println(authentication);
		
    //     if (authentication.getPrincipal() != "anonymousUser") {
    //         if (authentication.isAuthenticated()){
	// 			if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
    //                 .anyMatch(x -> x.toString().equals("admin"))) {

    //                 User currentUser = (User) authentication.getPrincipal();
    //                 int playerLoggedId = adminService.getIdAdminByName(currentUser.getUsername());
    //                 model.put("id",playerLoggedId);
                    
    //             } else {
    //                 User currentUser = (User) authentication.getPrincipal();
	// 			    int playerLoggedId = playerService.getIdPlayerByName(currentUser.getUsername());
	// 			    model.put("id",playerLoggedId);
    //             }
	// 		}
	// 	}
	// }

    // public void insertIdUserModelMap(ModelMap model){
		
	// 	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// 	System.out.println(authentication);
	// 	if (authentication.getPrincipal() != "anonymousUser") {
    //         if (authentication.isAuthenticated()){
    //             if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
    //                 .anyMatch(x -> x.toString().equals("admin"))) {

    //                 User currentUser = (User) authentication.getPrincipal();
    //                 int playerLoggedId = adminService.getIdAdminByName(currentUser.getUsername());
    //                 model.put("id",playerLoggedId);
                    
    //             } else {
    //                 User currentUser = (User) authentication.getPrincipal();
	// 			    int playerLoggedId = playerService.getIdPlayerByName(currentUser.getUsername());
	// 			    model.put("id",playerLoggedId);
    //             }
	// 		}
	// 	}
	// }

    


}

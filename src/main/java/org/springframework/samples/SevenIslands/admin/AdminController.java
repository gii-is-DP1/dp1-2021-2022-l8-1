package org.springframework.samples.SevenIslands.admin;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired	
	private SecurityService securityService;

    @Autowired
	private GameService gameService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired	
    private UserService userService;

    private static final String VIEWS_ADMINS_CREATE_OR_UPDATE_FORM = "admins/createOrUpdateAdminForm";
    private static final String ADMIN_NOT_FOUND = "admin not found";
    private static final String ADMIN = "admin";
    private static final String MESSAGE = "message";
    private static final String ERROR = "/error";


    
    @GetMapping(path="/profile/{adminId}")
    public String profile(@PathVariable("adminId") int adminId, ModelMap modelMap, HttpServletRequest request) {
        // if the user is not an admin, it will return an error page (only admins can access this page)

        securityService.insertIdUserModelMap(modelMap); 
        Optional<Admin> admin = adminService.findAdminById(adminId);
        
        if(admin.isPresent()){
            modelMap.addAttribute(ADMIN, admin.get());
        
        } else{
            modelMap.addAttribute(MESSAGE, ADMIN_NOT_FOUND);
            return ERROR;
        }
        return "admins/profile";
    }


    @GetMapping(path="/rooms")
    public String rooms(ModelMap modelMap, HttpServletRequest request) {

        Iterable<Game> games = gameService.findAll();
        modelMap.addAttribute(MESSAGE, request.getSession().getAttribute(MESSAGE));
        modelMap.addAttribute("games", games);
        request.getSession().removeAttribute(MESSAGE);
        return "admins/rooms";
    }


    @GetMapping(value = "/profile/edit/{adminId}")
    public String updateAdmin(@PathVariable("adminId") int adminId, ModelMap modelMap) {
        securityService.insertIdUserModelMap(modelMap);
        Optional<Admin> admin = adminService.findAdminById(adminId);
        if(admin.isPresent()){
            modelMap.addAttribute(ADMIN, admin.get());
        }else{
            modelMap.addAttribute(MESSAGE, ADMIN_NOT_FOUND);
            return ERROR;
        }
        return VIEWS_ADMINS_CREATE_OR_UPDATE_FORM;
    }
    
    
    @PostMapping(value = "/profile/edit/{adminId}")
	public String processUpdateForm(@Valid Admin admin, BindingResult result,@PathVariable("adminId") int adminId, ModelMap model) {
		
        if (result.hasErrors()) {
			model.put("player", admin);
			return VIEWS_ADMINS_CREATE_OR_UPDATE_FORM;
		}
		else {
            Optional<Admin> opt = adminService.findAdminById(adminId);
            if(opt.isPresent()) {
                Admin adminToUpdate = opt.get();
                // always present because if not, it should have redirected to error page in @GetMapping before
                   
                int authId = adminToUpdate.getUser().getAuthorities().iterator().next().getId();
                String userName = adminToUpdate.getUser().getUsername();

                BeanUtils.copyProperties(admin, adminToUpdate, "id");                                                                                  
                        try {                    
                            adminService.save(adminToUpdate);
                            userService.saveUser(adminToUpdate.getUser());
                            
                            authoritiesService.saveAuthorities(adminToUpdate.getUser().getUsername(), ADMIN);  
                            authoritiesService.deleteAuthorities(authId);
                            
                            if(!userName.equals(adminToUpdate.getUser().getUsername())){   
                                userService.delete(userName);
                            }
                            
                        } catch (Exception ex) {
                            result.rejectValue("name", "duplicate", "already exists");
                            log.error("already exists");
                            return VIEWS_ADMINS_CREATE_OR_UPDATE_FORM ;
                        }

                        if(!userName.equals(adminToUpdate.getUser().getUsername())){
                            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
                            return "redirect:/welcome";
                        }
                return "redirect:/admins/profile/" + adminId;
            } else {
                model.put(MESSAGE, ADMIN_NOT_FOUND);
                return ERROR;
            }
            
		}
	}

}

package org.springframework.samples.SevenIslands.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.Admin;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.userdetails.User;

@Controller
@RequestMapping("/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
    private AdminService adminService;

    //Vistas solo para admins:
    @GetMapping()
    public String listAchievements(ModelMap modelMap){
        String view = "achievements/listAchievements";
        //si accede un admin:
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    Iterable<Achievement> achievements = achievementService.findAll();
                    modelMap.addAttribute("achievements", achievements);
            
        }else{
            view = "/errors";
        }
        
        
        
        return view;
    }

    @GetMapping(path="/new")
    public String createAchievement(ModelMap modelMap){
        // String view="achievements/editAchievement";
        String view= "achievements/createOrUpdateAchievementForm";
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    modelMap.addAttribute("achievement", new Achievement());
        }else{
            view= "/errors";
        }
        return view;
    }

    @PostMapping(path="/save")
    public String saveAchievement(@Valid Achievement achievement, BindingResult result, ModelMap modelMap){
        String view= "achievements/listAchievements";
       
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    if(result.hasErrors()){
                        System.out.print(result.getAllErrors());
                        modelMap.addAttribute("achievement", achievement);
                        return "achievements/editAchievement";
                    }else{

                        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        // User currentUser = (User) authentication.getPrincipal();
                        
                        // Achievement ach = achievement;
                        // Admin admin = adminService.getAdminByName(currentUser.getUsername()).stream().findFirst().get(); 
                        // sé que el usuario es un admin, de otra forma no habría entrado en este bloque

                        //ach.addAdminInAchievements(admin);  
                        //admin.addAchievementInAdmins(ach); 

                        achievementService.save(achievement);
                        modelMap.addAttribute("message", "Achievement succesfully saved!");
                        view=listAchievements(modelMap);
                    }
        }else{
            view ="/errors";
        }
        return view;
    }
    @GetMapping(path="/delete/{achievementId}")
    public String deleteAchievement(@PathVariable("achievementId") int achievementId, ModelMap modelMap){
        
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    Optional<Achievement> achievement = achievementService.findAchievementById(achievementId);
                    if(achievement.isPresent()){    // porque es un optional
                        achievementService.delete(achievement.get());
                        modelMap.addAttribute("message", "Achievement successfully deleted!");


                    }else{
                        modelMap.addAttribute("message", "Achievement not found");
                        listAchievements(modelMap);
                    }
        }else{
            return "/errors";
        }
        return "redirect:/achievements";

    }

    private static final String VIEWS_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM = "achievements/createOrUpdateAchievementForm";

    @GetMapping(path="/edit/{achievementId}")
    public String updateAchievement(@PathVariable("achievementId") int achievementId, ModelMap model) {
        String view = VIEWS_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM;
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    Achievement achievement = achievementService.findAchievementById(achievementId).get();
                    model.put("achievement", achievement);
        }else{
            view = "/errors";
        }
        return view;
    }

    /**
     *
     * @param achievement
     * @param result
     * @param achievementId
     * @param model
     * @param surname
     * @param firstName
     * @param model
     * @return
     */

    @PostMapping(value = "/edit/{achievementId}")
	public String processUpdateForm(@Valid Achievement achievement, BindingResult result,@PathVariable("achievementId") int achievementId, ModelMap model) {
		//Si no es admin:
        if (!SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(x -> x.toString().equals("admin"))) {
                    return "redirect:/errors";
        }
        if (result.hasErrors()) {
            System.out.print(result.getAllErrors());
			model.put("achievement", achievement);
			return VIEWS_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM;
		}
		else {
            Achievement achievementToUpdate=this.achievementService.findAchievementById(achievementId).get();
			BeanUtils.copyProperties(achievement, achievementToUpdate, "id");                                                                                  
                    try {                    
                        this.achievementService.save(achievementToUpdate);                    
                    
                    } catch (Exception ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEWS_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM;
                    }
			return "redirect:/achievements";
		}
	}

    //Vistas para jugadores
    @GetMapping(path = "/{id}") 
    public String listMyAchievements(ModelMap modelMap, @PathVariable("id") int id) {
        
        String vista = "achievements/MyAchievements";
    
        Iterable<Achievement> arch = achievementService.findByPlayerId(id);//logros completados
        Iterable<Achievement> arch2 = achievementService.findAll();//logros totales
        
        List<Achievement> totales = new ArrayList<>();
        List<Achievement> conseguidos = new ArrayList<>();
        for(Achievement a : arch2){
            totales.add(a);
        }
        for(Achievement a : arch){
            conseguidos.add(a);
        }
        List<Achievement> sol = totales.stream().filter(x->!conseguidos.contains(x)).collect(Collectors.toList());


        modelMap.addAttribute("conseguidos", conseguidos); 
        modelMap.addAttribute("noc", sol);  
        
        return vista;
    }
}
        

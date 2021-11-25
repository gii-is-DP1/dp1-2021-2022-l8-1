package org.springframework.samples.SevenIslands.achievement;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
	private GeneralService generalService;

    @GetMapping()
    public String listAchievements(ModelMap modelMap){
        String view = "achievements/achievements";
        generalService.insertIdUserModelMap(modelMap);
        
        Iterable<Achievement> achievements = achievementService.findAll();
        modelMap.addAttribute("achievements", achievements);
        
        return view;
    }

    @GetMapping(path="/new")
    public String createAchievement(ModelMap modelMap){
        // String view="achievements/editAchievement";
        String view= "achievements/createOrUpdateAchievementForm";
        generalService.insertIdUserModelMap(modelMap);
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
        String view= "achievements/listAchievements";
        generalService.insertIdUserModelMap(modelMap);
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
        generalService.insertIdUserModelMap(model);
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

}
        

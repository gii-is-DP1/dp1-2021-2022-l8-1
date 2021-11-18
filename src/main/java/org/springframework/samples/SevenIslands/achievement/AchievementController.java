package org.springframework.samples.SevenIslands.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    //Vistas solo para admins:
    @GetMapping("/achievementsAdmins")
    public String listAchievements(ModelMap modelMap){
        String vista = "achievements/listAchievements";
        Iterable<Achievement> achievements = achievementService.findAll();
        modelMap.addAttribute("achievements", achievements);
        return vista;
    }

    @GetMapping(path="/achievementsAdmins/new")
    public String createAchievement(ModelMap modelMap){
        String view="achievements/editAchievement";
        modelMap.addAttribute("achievement", new Achievement());
        return view;
    }

    @PostMapping(path="/achievementsAdmins/save")
    public String saveAchievement(@Valid Achievement achievement, BindingResult result, ModelMap modelMap){
        String view= "achievements/listAchievements";
        if(result.hasErrors()){
            System.out.print(result.getAllErrors());
            modelMap.addAttribute("achievement", achievement);
            return "achievements/editAchievement";
        }else{
            achievementService.save(achievement);
            modelMap.addAttribute("message", "Achievement succesfully saved!");
            view=listAchievements(modelMap);
        }
        return view;
    }
    @GetMapping(path="/achievementsAdmins/delete/{achievementId}")
    public String deleteAchievement(@PathVariable("achievementId") int achievementId, ModelMap modelMap){
        String view= "achievements/listAchievements";
        Optional<Achievement> achievement = achievementService.findAchievementById(achievementId);
        if(achievement.isPresent()){
            achievementService.delete(achievement.get());
            modelMap.addAttribute("message", "Achievement successfully deleted!");
        }else{
            modelMap.addAttribute("message", "Achievement not found");
            view=listAchievements(modelMap);
        }
        return view;

    }

    private static final String VIEWS_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM = "achievements/createOrUpdateAchievementForm";

    @GetMapping(path="/achievementsAdmins/edit/{achievementId}")
    public String updateAchievement(@PathVariable("achievementId") int achievementId, ModelMap model) {
        Achievement achievement = achievementService.findAchievementById(achievementId).get(); // optional puede ser error el import
        model.put("achievement", achievement);
        return VIEWS_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM;
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

    @PostMapping(value = "/achievementsAdmins/edit/{achievementId}")
	public String processUpdateForm(@Valid Achievement achievement, BindingResult result,@PathVariable("achievementId") int achievementId, ModelMap model) {
		if (result.hasErrors()) {
            System.out.print(result.getAllErrors());
			model.put("achievement", achievement);
			return VIEWS_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM;
		}
		else {
                    Achievement achievementToUpdate=this.achievementService.findAchievementById(achievementId).get();
			BeanUtils.copyProperties(achievement, achievementToUpdate, "id","achievement","achievements","code");                                                                                  
                    try {                    
                        this.achievementService.save(achievementToUpdate);                    
                    
                    } catch (Exception ex) {
                        result.rejectValue("name", "duplicate", "already exists");
                        return VIEWS_ACHIEVEMENTS_CREATE_OR_UPDATE_FORM;
                    }
			return "redirect:/achievements/achievementsAdmins";
		}
	}

    //Vistas para jugadores
    @GetMapping(path = "/achievementsPlayers/{id}") 
    public String myArchievement(ModelMap modelMap, @PathVariable("id") int id) {
        
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
        

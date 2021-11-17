package org.springframework.samples.SevenIslands.achievement;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @GetMapping()
    public String listAchievements(ModelMap modelMap){
        String vista = "achievements/listAchievements";
        Iterable<Achievement> achievements = achievementService.findAll();
        modelMap.addAttribute("achievements", achievements);
        return vista;
    }

    @GetMapping(path="/new")
    public String createAchievement(ModelMap modelMap){
        String view="achievements/editAchievement";
        modelMap.addAttribute("achievement", new Achievement());
        return view;
    }

    @PostMapping(path="/save")
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


    
}

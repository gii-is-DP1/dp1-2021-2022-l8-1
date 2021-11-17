package org.springframework.samples.SevenIslands.achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
    
        @GetMapping(path = "/{id}") 
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
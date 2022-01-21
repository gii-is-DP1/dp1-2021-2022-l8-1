package org.springframework.samples.SevenIslands.achievement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/achievements")
public class AchievementController {

    @Autowired
    private AchievementService achievementService;

    @Autowired
	private SecurityService securityService;

    private static final String CREATE_OR_UPDATE_ACHIEVEMENTS_FORM = "achievements/createOrUpdateAchievementForm";

 
    private static final String REDIRECT_TO_ACHIEVEMENTS = "redirect:/achievements";
    private static final String ACHIEVEMENT = "achievement";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MESSAGE = "message";
    private static final String ERROR = "/error";

    @GetMapping()
    public String listAchievements(ModelMap modelMap, HttpServletRequest request) {

        if(securityService.isAdmin()) {
            String view = "achievements/achievements";
            securityService.insertIdUserModelMap(modelMap);
            modelMap.addAttribute(MESSAGE, request.getSession().getAttribute(MESSAGE));
            
            Iterable<Achievement> achievements = achievementService.findAll();
            modelMap.addAttribute("achievements", achievements);
            request.getSession().removeAttribute(MESSAGE);
            
            return view;
        
        } else {    // never should enter here because we specified that only admins have access to this page in SecurityConfiguration.java
            request.getSession().setAttribute(MESSAGE, "You don't have permission to access this page!");
            return "redirect:/welcome";  
        } 

        
    }

    @GetMapping(path="/new")
    public String createAchievement(ModelMap modelMap){ 

        if(securityService.isAdmin()) {
            securityService.insertIdUserModelMap(modelMap);
            modelMap.addAttribute(ACHIEVEMENT, new Achievement());
            
        }else{  
            return ERROR;
        }
        return CREATE_OR_UPDATE_ACHIEVEMENTS_FORM;
    }

    @PostMapping(path="/save")
    public String saveAchievement(@Valid Achievement achievement, BindingResult result, ModelMap modelMap, HttpServletRequest request){

        if (securityService.isAdmin()) {
            if(result.hasErrors()){

                modelMap.addAttribute(ACHIEVEMENT, achievement);
                return CREATE_OR_UPDATE_ACHIEVEMENTS_FORM;
            }else if(achievementService.achievementHasInappropiateWords(achievement)){
                modelMap.put(ACHIEVEMENT, achievement);
                modelMap.addAttribute(ERROR_MESSAGE, "The achievement contains inappropiate words. Please, check your language.");
                return CREATE_OR_UPDATE_ACHIEVEMENTS_FORM;
    
            }else{
                
                Iterable<Achievement> achievementsI = achievementService.findAll();
                List<Achievement> achievements = StreamSupport.stream(achievementsI.spliterator(), false).collect(Collectors.toList());

                for(Achievement a:achievements){
                    if(a.getParameter()==achievement.getParameter() && a.getMinValue().equals(achievement.getMinValue())){
                    
                        modelMap.put(ACHIEVEMENT, achievement);
                        modelMap.addAttribute(ERROR_MESSAGE, "Achievement already exist!");
                        return CREATE_OR_UPDATE_ACHIEVEMENTS_FORM;       
                    }
                }
                achievementService.save(achievement);
                request.getSession().setAttribute(MESSAGE, "Achievement successfully saved!");
             
            }
        }else{
            return ERROR;
        }
        return REDIRECT_TO_ACHIEVEMENTS;
    }


    @GetMapping(path="/delete/{achievementId}")
    public String deleteAchievement(@PathVariable("achievementId") int achievementId, ModelMap modelMap, HttpServletRequest request){

        if (securityService.isAdmin()) {
            securityService.insertIdUserModelMap(modelMap);
            Optional<Achievement> achievement = achievementService.findAchievementById(achievementId);

            if(achievement.isPresent()){   

                achievementService.delete(achievement.get());
                request.getSession().setAttribute(MESSAGE, "Achievement successfully deleted!");
            
            }else{
                request.getSession().setAttribute(MESSAGE, "Achievement not found");                      
            }
        }else{
            return ERROR;
        }
        return REDIRECT_TO_ACHIEVEMENTS;

    }


    @GetMapping(path="/edit/{achievementId}")
    public String updateAchievement(@PathVariable("achievementId") int achievementId, ModelMap model, HttpServletRequest request) {

        
        securityService.insertIdUserModelMap(model);


        if (securityService.isAdmin()) {
            securityService.insertIdUserModelMap(model);
            Optional<Achievement> achievement = achievementService.findAchievementById(achievementId);
            if(achievement.isPresent()){
                model.put(ACHIEVEMENT, achievement.get());
                
            } else {
                request.getSession().setAttribute(MESSAGE, "Achievement not found!");
                return REDIRECT_TO_ACHIEVEMENTS;
            }

        }else {
            return ERROR;
        }
        return CREATE_OR_UPDATE_ACHIEVEMENTS_FORM;
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
	public String processUpdateForm(@Valid Achievement achievement, BindingResult result,@PathVariable("achievementId") int achievementId, ModelMap model, HttpServletRequest request,
                                        @RequestParam(value="version", required = false) Integer version) {

        if (result.hasErrors()) {
			model.put(ACHIEVEMENT, achievement);
			return CREATE_OR_UPDATE_ACHIEVEMENTS_FORM;
		
        } else {
            Optional<Achievement> opt = achievementService.findAchievementById(achievementId);
            if(opt.isPresent()) {
                Achievement achievementToUpdate= opt.get();   
                // always present because if not, it should have redirected to achievements page with the message "Achievement not found!" in the @GetMapping before
                
                if(!achievementToUpdate.getVersion().equals(version)){    //Version
                    model.put(MESSAGE, "Concurrent modification of achievement! Try again!");
                    return updateAchievement(achievementToUpdate.getId(),model,request);
                }else if(achievementService.achievementHasInappropiateWords(achievement)){
                    model.put(ACHIEVEMENT, achievement);
                    model.addAttribute(ERROR_MESSAGE, "The achievement contains inappropiate words. Please, check your language.");
                    return CREATE_OR_UPDATE_ACHIEVEMENTS_FORM;
        
                }
    
                BeanUtils.copyProperties(achievement, achievementToUpdate, "id");                                                                                  
                        try {                    
                            achievementService.save(achievementToUpdate);        
                            request.getSession().setAttribute(MESSAGE, "Achievement successfully updated!");            
                        
                        } catch (Exception ex) {
                            result.rejectValue("name", "duplicate", "already exists");
                            log.error("achievement already exists");
                            return CREATE_OR_UPDATE_ACHIEVEMENTS_FORM;
                        }
                return REDIRECT_TO_ACHIEVEMENTS;
            } else {
                request.getSession().setAttribute(MESSAGE, "Achievement not found!");
                return REDIRECT_TO_ACHIEVEMENTS;
            }
		}
	}

}
        

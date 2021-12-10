package org.springframework.samples.SevenIslands.ranking;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private SecurityService securityService;

    @GetMapping()
    public String ranking(ModelMap modelMap, HttpServletRequest request) {
        
        if(securityService.isAuthenticatedUser()) {
            Collection<Player> playersWins =  playerService.getTwentyBestPlayersByWins();
            Collection<Player> playersPoints =  playerService.getTwentyBestPlayersByPoints();

            modelMap.addAttribute("playersWins", playersWins);
            modelMap.addAttribute("playersPoints", playersPoints);

        } else {    // never should enter here because the user is redirected to the login page (we specified that only players and admins can access this page)
            return securityService.redirectToWelcome(request);
        }
        
        return "statistics/ranking";
    }

}

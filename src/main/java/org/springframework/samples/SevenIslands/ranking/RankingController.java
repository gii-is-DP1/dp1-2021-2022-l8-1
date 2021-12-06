package org.springframework.samples.SevenIslands.ranking;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ranking")
public class RankingController {

    @Autowired
    PlayerService playerService;

    @GetMapping()
    public String ranking(ModelMap modelMap) {
        String view = "statistics/ranking";

        Collection<Player> playersWins =  playerService.getTwentyBestPlayersByWins();
        Collection<Player> playersPoints =  playerService.getTwentyBestPlayersByPoints();

        modelMap.addAttribute("playersWins", playersWins);
        modelMap.addAttribute("playersPoints", playersPoints);
        
        return view;
    }

}

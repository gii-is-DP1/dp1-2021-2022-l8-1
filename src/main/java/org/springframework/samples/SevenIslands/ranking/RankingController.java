package org.springframework.samples.SevenIslands.ranking;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ranking")
public class RankingController {
    @GetMapping()
    public String ranking(ModelMap modelMap) {
        String view = "statistics/ranking";
        //TODO: Create methods in playerService
        return view;
    }

}

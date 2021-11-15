package org.springframework.samples.SevenIslands.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/players")
public class PlayerController {

    private static final String VIEWS_PROFILE = "player/profile";

	@Autowired
	public PlayerController() {}

    @GetMapping(value = "/profile")
	public String profile(ModelMap model) {
		// (ModelMap model)
		// Pet pet = new Pet();
		// owner.addPet(pet);
		// model.put("pet", pet);
		return VIEWS_PROFILE;
	}
}

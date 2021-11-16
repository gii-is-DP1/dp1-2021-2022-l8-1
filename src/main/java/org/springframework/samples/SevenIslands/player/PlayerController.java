package org.springframework.samples.SevenIslands.player;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@RequestMapping("/players")
public class PlayerController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "players/createOrUpdatePlayerForm";
	
	private static final String VIEWS_PROFILE = "players/profile";

	private final PlayerService playerService;
	
	@Autowired
	public PlayerController(PlayerService playerService, UserDetailsService userService) {
		this.playerService = playerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping(value = "/profile")
	public String profile(ModelMap model) {
		// (ModelMap model)
		// Pet pet = new Pet();
		// owner.addPet(pet);
		// model.put("pet", pet);
		return VIEWS_PROFILE;
	}

	@GetMapping(value = "/players/new")
	public String initCreationForm(Map<String, Object> model) {
		Player player = new Player();
		model.put("player", player);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/players/new")
	public String processCreationForm(@Valid Player player, BindingResult result) {
		if (result.hasErrors()) {
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n" + result);
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			System.out.println("hello\n\n\n\n\n\n\n");
			//creating player, user and authorities
			this.playerService.savePlayer(player);
			
			return "redirect:/players/" + player.getId();
		}
	}

	@GetMapping(value = "/players/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("player", new Player());
		return "players/findPlayers";
	}

	@GetMapping(value = "/players")
	public String processFindForm(Player player, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (player.getSurname() == null) {
			player.setSurname(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Player> results = this.playerService.findPlayerBySurname(player.getSurname());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("surname", "notFound", "not found");
			return "players/findPlayers";
		}
		else if (results.size() == 1) {
			// 1 player found
			player = results.iterator().next();
			return "redirect:/players/" + player.getId();
		}
		else {
			// multiple owners found
			model.put("selections", results);
			return "players/ownersList";
		}
	}

	@GetMapping(value = "/players/{playerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("playerId") int playerId, Model model) {
		Optional<Player> player = this.playerService.findPlayerById(playerId);
		model.addAttribute(player.get());
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/players/{playerId}/edit")
	public String processUpdateOwnerForm(@Valid Player player, BindingResult result,
			@PathVariable("playerId") int playerId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			player.setId(playerId);
			this.playerService.savePlayer(player);
			return "redirect:/players/{playerId}";
		}
	}

	/**
	 * Custom handler for displaying an player.
	 * @param ownerId the ID of the player to display
	 * @return a ModelMap with the model attributes for the view
	 */
	@GetMapping("/players/{playerId}")
	public ModelAndView showOwner(@PathVariable("playerId") int playerId) {
		ModelAndView mav = new ModelAndView("players/playerDetails");
		mav.addObject(this.playerService.findPlayerById(playerId));
		return mav;
	}

}
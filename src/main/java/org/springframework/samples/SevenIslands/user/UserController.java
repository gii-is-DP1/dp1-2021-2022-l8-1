/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.SevenIslands.user;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.ModelMap;

@Controller
public class UserController {

	private static final String VIEWS_PLAYER_CREATE_FORM = "players/createOrUpdatePlayerForm";

	private final PlayerService playerService;

	@Autowired
	public UserController(PlayerService playerService) {
		this.playerService = playerService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	private static final String PLAYER = "player";
    private static final String ERROR_MESSAGE = "errorMessage";

	@GetMapping(value = "/users/new")
	public String initCreationForm(Map<String, Object> model) {
		Player player = new Player();
		model.put(PLAYER, player);
		return VIEWS_PLAYER_CREATE_FORM;
	}

	@PostMapping(value = "/users/new")
	public String processCreationForm(@Valid Player player, BindingResult result, ModelMap model) {
		String view = VIEWS_PLAYER_CREATE_FORM;
		String username = player.getUser().getUsername().trim();	
		if(playerService.playerHasInappropiateWords(player)){
			model.addAttribute(ERROR_MESSAGE, "Your profile can't contain inappropiate words. Please, check your language.");
			model.put(PLAYER, player);
			return view;
		}
		Boolean isNewUser = true;
		if(playerService.emailAlreadyused(player.email, player, isNewUser)){
            model.addAttribute(ERROR_MESSAGE, "Email already used by other user");
            model.put(PLAYER, player);
			return VIEWS_PLAYER_CREATE_FORM;
        }
		if(playerService.usernameAlreadyused(player.getUser().getUsername(), player.getUser(), isNewUser)){
            model.addAttribute(ERROR_MESSAGE, "Username already used by other user");
            model.put(PLAYER, player);
			return VIEWS_PLAYER_CREATE_FORM;
        }
		if (result.hasErrors()) {
			return view;
		} else if(username.contains(" ")){
			model.addAttribute(ERROR_MESSAGE, "Your username can't contain empty spaces. ");
			model.put(PLAYER, player);
			return view;

		} else {
			//creating player, user, and authority
			this.playerService.savePlayer(player);
			return "redirect:/";
		}
	}

}

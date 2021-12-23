package org.springframework.samples.SevenIslands.web.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.web.jsonview.Views;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjaxPlayersResponseBody{

	@JsonView(Views.Public.class)
	String msg;
	
	@JsonView(Views.Public.class)
	String code;

	@JsonView(Views.Public.class)
	List<Player> players;

}

package org.springframework.samples.SevenIslands.web.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.web.jsonview.Views;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AjaxGameResponseBody{

	@JsonView(Views.Public.class)
	String msg;
	
	@JsonView(Views.Public.class)
	String code;

	@JsonView(Views.Public.class)
	Game game;

	@JsonView(Views.Public.class)
	Integer numberOfPlayers;

	@JsonView(Views.Public.class)
	Integer actualPlayer;
	
	@JsonView(Views.Public.class)
	List<Integer> playersIds;

}

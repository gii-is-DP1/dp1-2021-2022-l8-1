package org.springframework.samples.SevenIslands.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.person.Person;

import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class WelcomeController {
	
	//OJO QUE ESTO ES POR AHORA
	@Autowired	
	GameService gameService;

	@Autowired	
	GeneralService gService;

	@Autowired	
	UserService userService;

	

	
	//HASTA AQUI 


	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model, HttpServletResponse response) {
		  
		List<Person> persons = new ArrayList<Person>();
		Person a = new Person();
		Person b = new Person();
		Person c = new Person();
		Person d = new Person();
		Person e = new Person();
		Person f = new Person();


		a.setFirstName("Ismael");
		a.setSurname("Pérez");
		persons.add(a);

		b.setFirstName("Miguel");
		b.setSurname("Romero");
		persons.add(b);

		c.setFirstName("Ezequiel");
		c.setSurname("González");
		persons.add(c);

		d.setFirstName("Pablo");
		d.setSurname("Rivera");
		persons.add(d);

		e.setFirstName("Tomás");
		e.setSurname("Camero");
		persons.add(e);

		f.setFirstName("Juan");
		f.setSurname("Salado");
		persons.add(f);

		gService.insertIdUser(model);
		

		model.put("persons", persons);
		model.put("title", "Seven Islands");
		model.put("group", "L8-1 a.k.a. Dream Team");
		
		model.put("user", userService.findUser("test1").get().getUsername());
		
		
		return "welcome";

	  }
}


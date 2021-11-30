package org.springframework.samples.SevenIslands.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.general.GeneralService;
import org.springframework.samples.SevenIslands.person.Person;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	
	@Autowired	
	GameService gameService;

	@Autowired	
	GeneralService gService;

	@Autowired	
	UserService userService;

	

	  @GetMapping({"/","/welcome"})
	  public String welcome(ModelMap modelMap) {
		  
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

		gService.insertIdUser(modelMap);
		
		modelMap.addAttribute("persons", persons);
		modelMap.addAttribute("title", "Seven Islands");
		modelMap.addAttribute("group", "L8-1 a.k.a. Dream Team");


		
		
		
		
		return "welcome";

	  }
}


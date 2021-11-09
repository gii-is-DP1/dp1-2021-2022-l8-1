package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.samples.petclinic.model.Person1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.board.BoardService;
import org.springframework.samples.petclinic.game.GameService;
import org.springframework.samples.petclinic.model.Person1;


@Controller
public class WelcomeController {
	
	//OJO QUE ESTO ES POR AHORA
	@Autowired	
	GameService gameService;
	//HASTA AQUI 


	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model, HttpServletResponse response) {
		  
		List<Person1> persons = new ArrayList<Person1>();
		Person1 a = new Person1();
		Person1 b = new Person1();
		Person1 c = new Person1();
		Person1 d = new Person1();
		Person1 e = new Person1();
		Person1 f = new Person1();


		a.setFirstName("Ismael");
		a.setLastName("Pérez");
		persons.add(a);

		b.setFirstName("Miguel");
		b.setLastName("Romero");
		persons.add(b);

		c.setFirstName("Ezequiel");
		c.setLastName("González");
		persons.add(c);

		d.setFirstName("Pablo");
		d.setLastName("Rivera");
		persons.add(d);

		e.setFirstName("Tomás");
		e.setLastName("Camero");
		persons.add(e);

		f.setFirstName("Juan");
		f.setLastName("Salado");
		persons.add(f);

		model.put("persons", persons);
		model.put("title", "Board Game");
		model.put("group", "L8-1 a.k.a. Dream Team");
		
		//ESTO ES DE EJEMPLO
		response.addHeader("Refresh", "1");
		model.put("now", new Date());
		model.put("boardService", gameService.findGameById(1).get());
		//CUIDAO
		
		
		return "welcome";



		
	  }
}


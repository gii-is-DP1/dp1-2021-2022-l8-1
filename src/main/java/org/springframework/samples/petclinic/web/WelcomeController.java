package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.samples.petclinic.model.Person1;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {
		  
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
		return "welcome";
	  }
}


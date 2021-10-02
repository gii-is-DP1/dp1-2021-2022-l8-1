package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.samples.petclinic.model.Person;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {
		  
		List<Person> persons = new ArrayList<Person>();
		Person a = new Person();
		Person b = new Person();
		Person c = new Person();
		Person d = new Person();
		Person e = new Person();
		Person f = new Person();


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


package org.springframework.samples.SevenIslands.person;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.samples.SevenIslands.model.BaseEntity;
import org.springframework.samples.SevenIslands.web.jsonview.Views;

@MappedSuperclass
public class Person extends BaseEntity {

    @JsonView(Views.Public.class)
    @NotEmpty
    @Column(name = "first_name")
    private String firstName;
    
    @JsonView(Views.Public.class)
    @NotEmpty
    @Column(name = "surname")
    private String surname;
    
    @NotEmpty
    @Email
    @Column(unique = true, name = "email")
    private String email;


    public String getEmail() {
      return this.email;
    }
  
    public void setEmail(String email) {
      this.email = email;
    }

    public String getFirstName() {
		return this.firstName;
	  }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getSurname() {
      return this.surname;
    }

    public void setSurname(String surname) {
      this.surname = surname;
    }

}

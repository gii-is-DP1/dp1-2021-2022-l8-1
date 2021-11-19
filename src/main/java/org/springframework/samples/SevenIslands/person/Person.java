package org.springframework.samples.SevenIslands.person;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.SevenIslands.model.BaseEntity;

@MappedSuperclass
public class Person extends BaseEntity {

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;
    
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

    
    // public String getName() {
    //   return this.firstName;
    // }

    // public void setName(String Name) {
    //   this.firstName = Name;
    // }

}

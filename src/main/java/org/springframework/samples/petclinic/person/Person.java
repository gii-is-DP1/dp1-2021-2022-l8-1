package org.springframework.samples.petclinic.person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;

@Data
@Entity
public class Person extends NamedEntity {

    @NotEmpty
    private String firstName;
    
    @NotEmpty
    private String surname;

    @NotEmpty
    private String password;
    
    @Column(unique = true)
    private String userName;
    
    @NotEmpty
    @Email
    private String email;
}

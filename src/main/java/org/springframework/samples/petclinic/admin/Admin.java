package org.springframework.samples.petclinic.admin;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.person.Person;

import lombok.Data;

@Data
@Entity
@Table (name = "admins")
public class Admin extends Person{
    
    
}

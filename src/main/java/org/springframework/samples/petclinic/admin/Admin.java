package org.springframework.samples.petclinic.admin;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.samples.petclinic.person.Person;
import org.springframework.samples.petclinic.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "admins")
public class Admin extends Person{
    
    @ManyToMany
    private Collection<Player> players;
    
}

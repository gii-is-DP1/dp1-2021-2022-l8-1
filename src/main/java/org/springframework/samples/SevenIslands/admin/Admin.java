package org.springframework.samples.SevenIslands.admin;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import java.util.HashSet;
import java.util.Set;

import org.springframework.samples.SevenIslands.person.Person;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.achievement.Achievement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "admins")
public class Admin extends Person{

    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "admins_achievements", joinColumns = @JoinColumn(name = "admin_id"),
			inverseJoinColumns = @JoinColumn(name = "achievement_id"))
	private Set<Achievement> achievements;
    
    @ManyToMany
    private Collection<Player> players;
    
}

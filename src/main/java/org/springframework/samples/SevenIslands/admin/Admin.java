package org.springframework.samples.SevenIslands.admin;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.samples.SevenIslands.person.Person;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.achievement.Achievement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "admins")
public class Admin extends Person{

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "username", referencedColumnName = "username")
	  private User user;
	  
    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "admins_achievements", joinColumns = @JoinColumn(name = "admin_id"),
			inverseJoinColumns = @JoinColumn(name = "achievement_id"))
	private List<Achievement> achievements;


	public void addAchievementInAdmins(Achievement achi){
        if(this.getAchievements()==null){
            List<Achievement> l = new ArrayList<>();
            l.add(achi);
            this.setAchievements(l);     
        }else{
            List<Achievement> l = this.getAchievements();
            l.add(achi);
            this.setAchievements(l);
        }
    }

    
}

package org.springframework.samples.SevenIslands.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.person.Person;
import org.springframework.samples.SevenIslands.statistic.Statistic;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.web.jsonview.Views;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Audited
@Entity
@Table(name="players")
public class Player extends Person{
  
  @NotAudited
  @Version
	protected Integer version;

  @JsonView(Views.Public.class)
  @Column(name="profile_photo")
  private String profilePhoto;

  @Column(name="in_game")   //Player in a game  
  private Boolean inGame=false;

  // RELATION WITH STATISTIC
  @OneToMany(mappedBy = "player", fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
  @NotAudited
  private Set<Statistic> statistic;

  //RELATION WITH LOGROS
	@ManyToMany(fetch = FetchType.LAZY)
	@NotAudited
  @JoinTable(name = "players_achievements", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "achievement_id"))
	private Set<Achievement> achievements;

  //RELATION WITH CARTAS
  @ManyToMany(fetch = FetchType.EAGER)
	@NotAudited
  private List<Card> cards;

  //RELATION WITH GAMES 
	@ManyToMany(mappedBy = "players",cascade = CascadeType.ALL)
	@NotAudited
  private Collection<Game> games;

  public Player(String firstName, String surname, String email, String profilePhoto2, User user2) {
    this.firstName = firstName;
    this.surname=surname;
    this.email=email;
    this.profilePhoto=profilePhoto2;
    this.user=user2;
  }

  public Player(){
    
  }

  public void addGameinGames(Game game){
    List<Game> g = new ArrayList<>(this.getGames());
    g.add(game);
    this.setGames(g);
  }

  public void deleteGames(Collection<Game> g){
    this.games.removeAll(g);
  }

  //RELATION WITH USER
  @JsonView(Views.Public.class)
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

  public User getUser() {
		return user;
  }
  //SECOND RELATION WITH GAMES
  @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
  @NotAudited
  private Set<Game> gamesCreator;
	
}

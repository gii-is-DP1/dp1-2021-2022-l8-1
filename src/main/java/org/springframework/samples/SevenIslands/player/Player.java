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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.forum.Forum;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.person.Person;
import org.springframework.samples.SevenIslands.statistic.Statistic;
import org.springframework.samples.SevenIslands.user.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="players")
public class Player extends Person{
  
  @Column(name="profile_photo")
  // @NotEmpty
  private String profilePhoto;

  @Column(name="in_game")   //Player in a game  
  private Boolean inGame=true;

  // RELACION CON STATISTIC
  @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
  private Set<Statistic> statistic;

  //RELACION CON LOGROS
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_achievements", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "achievement_id"))
	private Set<Achievement> achievements;

  //RELACION CON CARTAS
  @ManyToMany(fetch = FetchType.EAGER)
	private List<Card> cards;


  //RELACION CON ESPECTADOR
  @ManyToOne(optional = true)
	private Game watchGames;

  //RELACION CON FOROS
  @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_forums", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "forum_id"))
	private Set<Forum> forums;

  //RELACION CON GAMES 
	@ManyToMany(mappedBy = "players",cascade = CascadeType.ALL) //PROBLEMA AQUI
	private Collection<Game> games;

  public void addGameinGames(Game game){
    List<Game> g = new ArrayList<>(this.getGames());
    g.add(game);
    this.setGames(g);
  }

  public void deleteGames(Collection<Game> g){
    this.games.removeAll(g);
  }

  // //RELACION CON PLAYER (Invitaciones)
  // @ManyToMany(fetch = FetchType.LAZY)
	// @JoinTable(name = "players_invitations", joinColumns = @JoinColumn(name = "invitation_id"),
	//  		inverseJoinColumns = @JoinColumn(name = "invited_id"))
  // private Collection<Player> invitations;

  // //RELACION CON PLAYER DE REQUEST
  // @ManyToMany(fetch = FetchType.LAZY)
	// @JoinTable(name = "players_requests", joinColumns = @JoinColumn(name = "friend_request_id"),
	// 		inverseJoinColumns = @JoinColumn(name = "requested_id"))
  // private Collection<Player> friend_requests;

  // //RELACION CON PLAYER (Amigos)
  // @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	// @JoinTable(name = "players_friends", joinColumns = @JoinColumn(name = "friend_id"),
	// 		inverseJoinColumns = @JoinColumn(name = "friend_identifier"))
  // private List<Player> players_friends;



  //RELACION CON USER
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

  public User getUser() {
		return user;
  }
  //SEGUNDA RELACION CON GAMES
  @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
  private Set<Game> gamesCreador;
	
}

package org.springframework.samples.SevenIslands.player;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.card.CARD_TYPE;

import org.springframework.samples.SevenIslands.card.Card;


import org.springframework.samples.SevenIslands.forum.Forum;

import org.springframework.samples.SevenIslands.game.Game;

import org.springframework.samples.SevenIslands.person.Person;
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

    @Column(name="total_games")
    // @NotEmpty
    private Integer totalGames;
    
    @Column(name="total_time_games")
    // @NotEmpty
    private Integer totalTimeGames;  //In seconds

    @Column(name="avg_time_games")
    // @NotEmpty
    private Double avgTimeGames;  //In seconds

    @Column(name="max_time_game")
    // @NotEmpty
    private Integer maxTimeGame;  //In seconds

    @Column(name="min_time_game")
    // @NotEmpty
    private Integer minTimeGame;  //In seconds

    @Column(name="total_points_all_games")
    // @NotEmpty
    private Integer totalPointsAllGames; 
    
    @Column(name="avg_total_points")
    // @NotEmpty
    private Double avgTotalPoints;
    
    @Column(name="favorite_island")
    // @NotEmpty
    @Min(value = 1)
    @Max(value = 7)
    private Integer favoriteIsland;

    @Column(name="favorite_treasure")
    @Enumerated(EnumType.STRING)
    // @NotEmpty
    private CARD_TYPE favoriteTreasure;

    @Column(name="max_points_of_games")
    // @NotEmpty
    private Integer maxPointsOfGames;

    @Column(name="min_points_of_games")
    // @NotEmpty
    private Integer minPointsOfGames;


  //RELACION CON LOGROS
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_achievements", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "achievement_id"))
	private Set<Achievement> achievements;

  //RELACION CON CARTAS
  @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_cards", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "card_id"))
	private Set<Card> cards;

  //RELACION CON ESPECTADOR
  @ManyToOne(optional = true)
	private Game watchGames;

  //RELACION CON FOROS
  @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_forums", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "forum_id"))
	private Set<Forum> forums;

  //RELACION CON GAMES
	@ManyToMany(mappedBy = "players")
	private Collection<Game> games;

  //RELACION CON PLAYER (Invitaciones)
  @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_invitations", joinColumns = @JoinColumn(name = "invitation_id"),
	 		inverseJoinColumns = @JoinColumn(name = "invited_id"))
  private Collection<Player> invitations;

  //RELACION CON PLAYER DE REQUEST
  @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_requests", joinColumns = @JoinColumn(name = "friend_request_id"),
			inverseJoinColumns = @JoinColumn(name = "requested_id"))
  private Collection<Player> friend_requests;

  //RELACION CON PLAYER (Amigos)
  @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_friends", joinColumns = @JoinColumn(name = "friend_id"),
			inverseJoinColumns = @JoinColumn(name = "friend_identifier"))
  private Collection<Player> players_friends;


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

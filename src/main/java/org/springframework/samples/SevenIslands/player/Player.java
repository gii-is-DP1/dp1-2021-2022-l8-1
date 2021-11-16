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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.admin.Admin;
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
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "username", referencedColumnName = "username")
	private User user;

  
  public User getUser() {
		return user;
	}


    @ManyToMany
    private Collection<Admin> admins;
 
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_achievements", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "achievement_id"))
	private Set<Achievement> achievements;

  @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_cards", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "card_id"))
	private Set<Card> cards;


    @ManyToOne(optional = true, cascade = CascadeType.ALL)
	private Game watchGames;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "players_forums", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "forum_id"))
	private Set<Forum> forums;

	@ManyToMany(mappedBy = "players", cascade = CascadeType.ALL)
	private Collection<Game> games;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "players_invitations", joinColumns = @JoinColumn(name = "invitation_id"),
	 		inverseJoinColumns = @JoinColumn(name = "invited_id"))
  private Collection<Player> invitations;

  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "players_requests", joinColumns = @JoinColumn(name = "friend_request_id"),
			inverseJoinColumns = @JoinColumn(name = "requested_id"))
  private Collection<Player> friend_requests;


}

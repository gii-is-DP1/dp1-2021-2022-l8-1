package org.springframework.samples.SevenIslands.statistic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.model.BaseEntity;
import org.springframework.samples.SevenIslands.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="statistics")
public class Statistic extends BaseEntity {

    @Autowired
	public Statistic(Player player) {
        this.player = player;
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "player_id")
    private Player player;

    @Column(name="games_count")
    @Formula("SELECT COUNT(G) FROM Games G WHERE G.player.statistic.id = id")
    private Integer gamesCount = 0; // In seconds

    @Column(name="games_count")
    @Formula("SELECT AVG(G) FROM Games G WHERE G.player.statistic.id = id GROUP BY ")
    private Double avgGameTime = 0.0; // In seconds
  
    // @Column(name="avg_time_games")
    // // @NotEmpty
    // private Double avgTimeGames = 0.0;  //In seconds
  
    // @Column(name="max_time_game")
    // // @NotEmpty
    // private Integer maxTimeGame = 0;  //In seconds
  
    // @Column(name="min_time_game")
    // // @NotEmpty
    // private Integer minTimeGame;  //In seconds
  
    // @Column(name="total_points_all_games")
    // // @NotEmpty
    // private Integer totalPointsAllGames = 0; 
    
    // @Column(name="avg_total_points")
    // // @NotEmpty
    // private Double avgTotalPoints = 0.0;
    
    // @Column(name="favorite_island")
    // // @NotEmpty
    // @Min(value = 1)
    // @Max(value = 7)
    // private Integer favoriteIsland;
  
    // @Column(name="favorite_treasure")
    // @Enumerated(EnumType.STRING)
    // // @NotEmpty
    // private CARD_TYPE favoriteTreasure;
  
    // @Column(name="max_points_of_games")
    // // @NotEmpty
    // private Integer maxPointsOfGames = 0;
  
    // @Column(name="min_points_of_games")
    // // @NotEmpty
    // private Integer minPointsOfGames = 0;
  
    // @Column(name="total_wins")
    // // @NotEmpty
    // private Integer totalWins = 0;
  
    // @Column(name="avg_wins")
    // // @NotEmpty
    // private Double avgWins = 0.0;
}

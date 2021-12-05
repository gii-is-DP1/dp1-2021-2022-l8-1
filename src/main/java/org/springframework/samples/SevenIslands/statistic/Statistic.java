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

	public Statistic(Player player) {
        this.player = player;
    }

    @OneToOne
    @MapsId
    @JoinColumn(name = "player_id")
    private Player player;
    
    @Column(name="favorite_island")
    // @NotEmpty
    @Min(value = 1)
    @Max(value = 7)
    private Integer favoriteIsland;
  
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

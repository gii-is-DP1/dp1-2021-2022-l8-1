package org.springframework.samples.petclinic.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.card.CARD_TYPE;
import org.springframework.samples.petclinic.person.Person;

import lombok.Data;

@Data
@Entity
@Table(name="players")
public class Player extends Person{
 
    @Column(name="profile_photo")
    @NotEmpty
    private String profilePhoto;

    @Column(name="total_games")
    @NotEmpty
    private Integer totalGames;
    
    @Column(name="total_time_games")
    @NotEmpty
    private Integer totalTimeGames;  //In seconds

    @Column(name="avg_time_games")
    @NotEmpty
    private Double avgTimeGames;  //In seconds

    @Column(name="max_time_game")
    @NotEmpty
    private Integer maxTimeGame;  //In seconds

    @Column(name="min_time_game")
    @NotEmpty
    private Integer minTimeGame;  //In seconds

    @Column(name="total_points_all_games")
    @NotEmpty
    private Integer totalPointsAllGames; 
    
    @Column(name="avg_total_points")
    @NotEmpty
    private Double avgTotalPoints;
    
    @Column(name="favorite_island")
    @NotEmpty
    @Min(value = 1)
    @Max(value = 7)
    private Integer favoriteIsland;

    @Column(name="favorite_treasure")
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private CARD_TYPE favoriteTreasure;

    @Column(name="max_points_of_games")
    @NotEmpty
    private Integer maxPointsOfGames;

    @Column(name="min_points_of_games")
    @NotEmpty
    private Integer minPointsOfGames; 

}

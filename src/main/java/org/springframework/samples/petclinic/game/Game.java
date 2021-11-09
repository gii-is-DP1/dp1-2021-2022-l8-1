package org.springframework.samples.petclinic.game;

import java.time.LocalDateTime;

import java.util.List;

import javax.persistence.Column;

//import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Game extends NamedEntity {

    @Column(unique = true, name = "code")
    private String code;
    
    @Column(name = "start_time") 
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime = LocalDateTime.now();
    
    @Column(name = "end_time") 
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime; //HABRIA QUE HACERLO CON UNA RESTA
    
    @Column(name = "number_of_players")   
    private Integer numberOfPlayers;

    @Column(name = "actual_player")   
    private Integer actualPlayer;

    @Column(name = "number_of_turn")   
    private Integer numberOfTurn;

    @Column(name = "remains_cards")   
    private Integer remainsCards;

    @Column(name = "deck")   
    private String deck;

    @Column(name = "players")   
    private String players;

    @Column(name = "points")   
    private String points; //pointsOfPlayers

    @Column(name = "privacity")   
    @Enumerated(EnumType.STRING)
    private PRIVACITY privacity;
    
}

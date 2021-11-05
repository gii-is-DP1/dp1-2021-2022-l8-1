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

@Data
@Entity
public class Game extends NamedEntity {

    @Column(unique = true)
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime = LocalDateTime.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime; //HABRIA QUE HACERLO CON UNA RESTA
    
    private Integer numberOfPlayers;
    private Integer actualPlayer;
    private Integer numberOfTurn;
    private Integer remainsCards;
    private String deck;
    private String players;
    private String points; //pointsOfPlayers

    @Enumerated(EnumType.STRING)
    private PRIVACITY privacity;
    
}

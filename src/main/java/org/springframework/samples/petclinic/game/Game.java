package org.springframework.samples.petclinic.game;

import java.time.LocalDateTime;

import javax.persistence.Column;

//import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;

@Data
@Entity
public class Game extends NamedEntity {
    private String name;

    @Column(unique = true)
    private String code;
    
    private Integer numberOfPlayers;
    private Integer numberOfTurn;
    private String actualPlayer;
    private Integer remainsCards;
     

     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
     private LocalDateTime startTime = LocalDateTime.now();

     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
     private LocalDateTime endTime;

     @Enumerated(EnumType.STRING)
     //@NotEmpty
     private PRIVACITY privacity;
    
}

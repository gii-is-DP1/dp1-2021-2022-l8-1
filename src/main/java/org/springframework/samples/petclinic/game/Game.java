package org.springframework.samples.petclinic.game;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;

//import java.time.LocalDateTime;

import javax.persistence.Entity;

import org.springframework.samples.petclinic.card.CARD_TYPE;
import org.springframework.samples.petclinic.card.Card;
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
    private String players;
    private Integer actualPlayer;
    private Integer remainsCards;
    
    // @ElementCollection(targetClass = CARD_TYPE.class)
    // private List<List<CARD_TYPE>> deck; 
    
    private String deck;//HEMOS SEPARADO LOS MAZOS POR PUNTO Y COMA Y LAS CARTAS DE CADA MAZO POR COMA, PARA DESPUES OBTENER EL QUE NOS HAGA FALTA Y PARSEARLO
    private String points; //HEMOS SEPARADO LOS PUNTOS POR UNA COMA PARA DESPUES OBTENER EL QUE NOS HAGA FALTA Y PARSEARLO

    
     

    // @DateTimeFormat(pattern = "yyyy/MM/dd")
    // private LocalDateTime startTime;

    // @DateTimeFormat(pattern = "yyyy/MM/dd")
    // private LocalDateTime endTime;
    
}

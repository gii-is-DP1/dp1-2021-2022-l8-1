package org.springframework.samples.petclinic.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;


import lombok.Data;

@Data
@Entity
public class Game extends NamedEntity{
    private String name;
    private String gameCode;
    private LocalDateTime startTime;
    private LocalDateTime EndTime;
    
}

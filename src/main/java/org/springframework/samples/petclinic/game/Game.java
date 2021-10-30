package org.springframework.samples.petclinic.game;

//import java.time.LocalDateTime;

import javax.persistence.Entity;

//import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;

@Data
@Entity
public class Game extends NamedEntity {
    private String name;
    private String code;

    // @DateTimeFormat(pattern = "yyyy/MM/dd")
    // private LocalDateTime startTime;

    // @DateTimeFormat(pattern = "yyyy/MM/dd")
    // private LocalDateTime endTime;
    
}

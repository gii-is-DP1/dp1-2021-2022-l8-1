package org.springframework.samples.petclinic.general;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;

@Data
@Entity
public class General extends NamedEntity {

    @NotEmpty
    @Column(name = "total_games")
    private String totalGames;
    
    @NotEmpty
    @Column(name = "total_duration_all_games")
    private Integer totalDurationAllGames; //In seconds
}

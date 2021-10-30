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
    private String totalGames;
    
    @NotEmpty
    private String totalDurationAllGames; //In seconds
}

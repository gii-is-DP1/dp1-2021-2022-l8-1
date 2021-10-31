package org.springframework.samples.petclinic.cell;


import javax.persistence.Entity;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;

@Data
@Entity
public class Cell extends NamedEntity {
    
    private Integer position;
    private String card;    
}


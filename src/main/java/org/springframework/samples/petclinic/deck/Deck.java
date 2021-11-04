package org.springframework.samples.petclinic.deck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "deck")
public class Deck extends NamedEntity {

    @Column(name="card_id")
    private Integer cardId;
    
    
}

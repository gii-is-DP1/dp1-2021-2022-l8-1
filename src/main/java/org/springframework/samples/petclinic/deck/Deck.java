package org.springframework.samples.petclinic.deck;


import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "deck")
public class Deck extends BaseEntity {

    
}

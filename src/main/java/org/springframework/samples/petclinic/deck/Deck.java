package org.springframework.samples.petclinic.deck;


import java.util.Collection;


import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deck")
public class Deck extends BaseEntity {

    @OneToMany
	private Collection<Card> cards;
    
}

package org.springframework.samples.SevenIslands.deck;


import java.util.Collection;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deck")
public class Deck extends BaseEntity {

    @OneToMany
    @JoinTable(name = "decks_cards", joinColumns = @JoinColumn(name = "deck_id"),
			inverseJoinColumns = @JoinColumn(name = "card_id"))
	private Collection<Card> cards;
    
}

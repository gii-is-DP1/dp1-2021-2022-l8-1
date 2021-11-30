package org.springframework.samples.SevenIslands.deck;


import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

    @Column(name="name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deck")
    // @JoinTable(name = "decks_cards", joinColumns = @JoinColumn(name = "deck_id"),
	// 		inverseJoinColumns = @JoinColumn(name = "card_id"))
	private Collection<Card> cards;

    public void deleteCards(Card card){
        cards.remove(card);
    }
    
}

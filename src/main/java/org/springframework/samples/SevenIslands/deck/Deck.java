package org.springframework.samples.SevenIslands.deck;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    
	private List<Card> cards;

    public void deleteCards(Card card){
        cards.remove(card);
    }
    
}

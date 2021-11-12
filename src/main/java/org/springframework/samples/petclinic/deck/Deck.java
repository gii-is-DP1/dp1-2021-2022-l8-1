package org.springframework.samples.petclinic.deck;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
import org.springframework.samples.petclinic.card.Card;
import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "deck")
public class Deck extends BaseEntity {

    // @OneToMany(fetch = FetchType.LAZY)
    // @JoinTable(name = "cards_deck", joinColumns = @JoinColumn(name = "deck_id"),
    //     inverseJoinColumns = @JoinColumn(name = "card_id"))
    // private List<Card> cards;
    
}

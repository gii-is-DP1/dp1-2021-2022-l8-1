package org.springframework.samples.SevenIslands.card;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.model.BaseEntity;
import org.springframework.samples.SevenIslands.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private CARD_TYPE cardType;

    @ManyToMany(mappedBy = "cards")
	private Set<Player> players;

    

    
}

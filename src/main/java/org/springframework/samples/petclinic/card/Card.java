package org.springframework.samples.petclinic.card;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.player.Player;

import lombok.Data;

@Data
@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private CARD_TYPE cardType;

    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "players_cards", joinColumns = @JoinColumn(name = "player_id"),
			inverseJoinColumns = @JoinColumn(name = "card_id"))
	private Set<Player> Player;
}

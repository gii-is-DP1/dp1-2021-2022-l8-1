package org.springframework.samples.SevenIslands.statistic;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.model.BaseEntity;
import org.springframework.samples.SevenIslands.player.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="statistics")
public class Statistic extends BaseEntity {

    @ManyToOne(optional=false)
    private Player player;

    @ManyToOne(optional=false)
    private Game game;

    @Column(name="points")
    public Integer points;
    
    @Column(name="had_won")
    public Boolean had_won;

    @Column(name="island_count")
    @ElementCollection
    @MapKeyJoinColumn(name="island_id")
    @CollectionTable(name="statistic_islands_count")
    public Map<Island,Integer> islandCount;
    
    @Column(name="card_count")
    @ElementCollection
    @MapKeyJoinColumn(name="card_id")
    @CollectionTable(name="statistic_cards_count")
    public Map<Card,Integer> cardCount;
}

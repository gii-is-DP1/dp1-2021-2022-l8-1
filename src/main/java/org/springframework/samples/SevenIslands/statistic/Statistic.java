package org.springframework.samples.SevenIslands.statistic;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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

    @OneToOne
    private Player player;

    @ManyToOne(optional=false)
    private Game game;
    

    @Column(name="points")
    public Integer points;
    
    @Column(name="had_won")
    public Integer hadWon;

    @Column(name="islands_count")
    public Map<Island,Integer> islandsCount;
    
    @Column(name="cards_count")
    public Map<Card,Integer> cardsCount;
}

package org.springframework.samples.SevenIslands.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.SevenIslands.board.Board;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.model.NamedEntity;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.web.jsonview.Views;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "games")
public class Game extends NamedEntity {

    @JsonView(Views.Public.class)
    @Column(unique = true, name = "code")
    private String code;

    @Column(name = "turn_time") 
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime turnTime = LocalDateTime.now();
    
    @Column(name = "start_time") 
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime = LocalDateTime.now();
    
    @Column(name = "end_time") 
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime; //Do with a substraction

    @Column(name="duration")
    private Integer duration = 0;

    @JsonView(Views.Public.class)
    @Column(name = "actual_player")   
    private Integer actualPlayer;

    @Column(name = "value_of_die")   
    private Integer valueOfDie = null;

    @Column(name = "die_throws")   
    private Boolean dieThrows = false;  //Actual player uses die in the actual turn?

    @Column(name = "privacity")   
    @Enumerated(EnumType.STRING)
    private PRIVACITY privacity;

    @JsonView(Views.Public.class)
    @Column(name="has_started")
    private boolean hasStarted;
    
    //RELATION WITH PLAYERS 
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "games_players", joinColumns = @JoinColumn(name="game_id"), 
                inverseJoinColumns = @JoinColumn(name="player_id"))
    private List<Player> players;

    public void addPlayerinPlayers(Player player){
        if(this.getPlayers()==null){
            List<Player> l = new ArrayList<>();
            l.add(player);
            this.setPlayers(l);     
        }else{
            List<Player> l = this.getPlayers();
            l.add(player);
            this.setPlayers(l);
        }
        
    }

    public void deletePlayerOfGame(Player player){
        this.players.remove(player);
    }

    @JsonView(Views.Public.class)
    @ManyToOne(optional=false)
    private Player player;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, optional=false)
    private Deck deck;

    public Deck getDeck() {
		return deck;

    }

    @OneToOne(cascade = CascadeType.ALL)
      private Board board;

   
}

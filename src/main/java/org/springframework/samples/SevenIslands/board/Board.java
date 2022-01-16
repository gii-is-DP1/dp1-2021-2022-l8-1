package org.springframework.samples.SevenIslands.board;

import javax.persistence.CascadeType;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="boards")
public class Board extends BaseEntity{
    String background;
    @Positive
    int width;
    @Positive
    int height;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Island> islands;


    public Board(){

        this.background="/resources/images/board.jpg";
        this.width=527;
        this.height=644;
    }

    public Board(List<Island> l){
        this.background="/resources/images/board.jpg";
        this.width=527;
        this.height=644;
        this.islands=l;
    }

}

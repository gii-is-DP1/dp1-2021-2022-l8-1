package org.springframework.samples.SevenIslands.board;

import javax.persistence.CascadeType;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import org.springframework.samples.SevenIslands.cell.Cell;

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

    public Board(){

        this.background="/resources/images/board.jpg";
        this.width=527;
        this.height=644;
    }

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "board",fetch = FetchType.EAGER)
    List<Cell> cells; 

}

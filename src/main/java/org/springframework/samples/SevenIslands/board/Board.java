package org.springframework.samples.SevenIslands.board;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

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
        this.background="resources/images/board.png";
        this.width=800;
        this.height=800;
    }
}

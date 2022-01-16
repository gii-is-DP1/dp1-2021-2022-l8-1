package org.springframework.samples.SevenIslands.board;

import java.util.List;

import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.util.BoardBuilderInterface;

public class BoardBuilder implements BoardBuilderInterface{
    private List<Island> islands;

    @Override
    public BoardBuilderInterface setIslands(List<Island> islands){
        this.islands=islands;
        return this;
    }

    @Override
    public Board build(){
        return new Board(islands);
    }
    
}

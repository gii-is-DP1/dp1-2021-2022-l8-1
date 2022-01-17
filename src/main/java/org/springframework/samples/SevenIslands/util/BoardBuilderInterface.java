package org.springframework.samples.SevenIslands.util;

import java.util.List;

import org.springframework.samples.SevenIslands.board.Board;
import org.springframework.samples.SevenIslands.island.Island;

public interface BoardBuilderInterface {
    BoardBuilderInterface setIslands(List<Island> islands);
    Board build();
    
}

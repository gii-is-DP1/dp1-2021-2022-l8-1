package org.springframework.samples.SevenIslands.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;
    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerService playerService;

    @Test
    public void testCountWithInitialData(){
        int count = boardService.boardCount();
        assertEquals(count, 1);
    }

    @Test
    public void testGetPointsPerSet(){
        Integer value = boardService.getPointsPerSet().get(1);
        Integer value2 = boardService.getPointsPerSet().get(4);
        assertEquals(value, 1);
        assertTrue(value2==13);
        assertFalse(value2==19);
    }

    @Test
    public void testBoardById(){ 
        Board board = boardService.findById(1).get();
        assertEquals(board.getHeight(),644);
    
    }
    @Test
    public void testLeaveGame(){ 
        String code = "ABCD124";
        List<Player> players = gameService.findGamesByRoomCode(code).iterator().next().getPlayers();
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + players.stream().map(x-> x.getUser()));
        Player player1 = playerService.findPlayerById(1).get();
        if(players.stream().anyMatch(x->x.equals(player1))){
            String none = boardService.leaveGame(player1, code);
            List<Player> actualPlayers = gameService.findGamesByRoomCode(code).iterator().next().getPlayers();
            assertTrue(actualPlayers.stream().noneMatch(x->x.equals(player1)));
        }
    }

    
}

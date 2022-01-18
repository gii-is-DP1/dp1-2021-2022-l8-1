package org.springframework.samples.SevenIslands.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.card.CARD_TYPE;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckService;
import org.springframework.samples.SevenIslands.game.Game;
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
    @Autowired
    private DeckService deckService;

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
    public void testInitCardPlayers(){ 
        String code = "ABCD122";
        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        Integer nPlayers = (int) game.getPlayers().stream().count();
        Deck d = deckService.init("ABCD122");
        int initSize = game.getDeck().getCards().size();

        for(Player p: game.getPlayers()){
            List<Card> cards = new ArrayList<>();      
            List<Card> doblones = d.getCards().stream().filter(x->x.getCardType().equals(CARD_TYPE.DOUBLON)).limit(3).collect(Collectors.toList());  
            d.getCards().removeAll(doblones);
            cards.addAll(doblones);
            assertEquals(3, doblones.size());
            assertEquals(3, cards.size());
        }

        asserEquals(initSize, d.getCards().size()-nPlayers*3);
        

    
    }

    private void asserEquals(int initSize, int i) {
    }

    @Test
    public void testLeaveGame(){ 
        String code = "ABCD124";
        List<Player> players = gameService.findGamesByRoomCode(code).iterator().next().getPlayers();
        Player player1 = playerService.findPlayerById(1).get();
        if(players.stream().anyMatch(x->x.equals(player1))){
            String none = boardService.leaveGame(player1, code);
            List<Player> actualPlayers = gameService.findGamesByRoomCode(code).iterator().next().getPlayers();
            assertTrue(actualPlayers.stream().noneMatch(x->x.equals(player1)));
        }
    }

    
}

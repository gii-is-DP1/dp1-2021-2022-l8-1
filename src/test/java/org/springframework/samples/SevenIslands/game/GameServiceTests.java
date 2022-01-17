package org.springframework.samples.SevenIslands.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckService;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTests {
    
    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private DeckService deckService;
    
    @Test
    public void testCountWithInitialData(){
        int count = gameService.gameCount();
        assertEquals(5, count);
    }

    @Test
    public void testFindGameById(){ 
        Game game = gameService.findGameById(1).get();
        assertThat(game.getName()).isEqualTo("Prueba0");
        assertThat(game.getCode()).isEqualTo("ABCD124");

    }

    @Test
    public void testFindAllPublicNotPlaying(){
        long count = gameService.findAllPublicNotPlaying().spliterator().getExactSizeIfKnown();
        assertEquals(2, count);
    }

    @Test
    public void testFindAll(){
        long count = gameService.findAll().spliterator().getExactSizeIfKnown();
        assertEquals(5, count);
    }

    @Test
    public void testFindAllPublic(){
        long count = gameService.findAllPublic().spliterator().getExactSizeIfKnown();
        assertEquals(3, count);
    }
    
    @Test
    public void testFindAllPlaying(){
        int count = gameService.findAllPlaying().size();
        assertEquals(2, count);
    }

    @Test
    public void testFindAllPublicPlaying(){
        int count = gameService.findAllPublicPlaying().size();
        assertEquals(1, count);
    }
    
    @Test
    public void testfindByOwnerId(){  // counts the games where the player as created
        Iterable<Game> games = gameService.findByOwnerId(1);
        long count = games.spliterator().getExactSizeIfKnown();
        assertEquals(4, count);
    }

    @Test
    public void testFindGamesCountByPlayerId(){
        int count = gameService.findGamesCountByPlayerId(1);
        assertEquals(5, count);
    }

    @Test
    public void testIsWaitingOnRoom(){
        Boolean waiting = gameService.isWaitingOnRoom(3); //Should be waiting as he's only in 2 rooms
        Boolean waiting2 = gameService.isWaitingOnRoom(2); //Should be waiting even if he is playing 1 room as he is waiting in another
        assertEquals(true, waiting);
        assertEquals(true, waiting2);
    }

    @Test
    public void testIsOwner(){
        Boolean isOwner = gameService.isOwner(1, 1);
        Boolean isNotOwner = gameService.isOwner(4,2);
        assertEquals(true, isOwner);
        assertEquals(false, isNotOwner);
    }

    @Test
    public void testWaitingRoom(){
        Integer gameId = gameService.waitingRoom(3).getId(); 
        assertEquals(2, gameId); //2 Because this method find the first one
    }
    
    @Test
    public void shouldInsertGame(){
        Game game = new Game();
        game.setName("Partida 1");
        Deck deck = deckService.init(game.getName());
        game.setCode("AAAABBBB1");
        game.setPrivacity(PRIVACITY.PUBLIC);
        game.setHas_started(false);
        game.setPlayer(playerService.findPlayerById(1).get());
        game.setDeck(deck);

        
        gameService.save(game);

        assertThat(game.getId().longValue()).isNotEqualTo(0);

        // assertThat(gameService.findGameByRoomCode(game.getCode()).stream().collect(Collectors.toList()).get(0).getCode().toString()).isEqualTo("AAAABBBB1");
        assertThat(gameService.findGamesByRoomCode(game.getCode()).iterator().next().getCode().toString()).isEqualTo("AAAABBBB1");


    }

    @Test
    public void shouldDeleteGame(){

        int countBefore = gameService.gameCount();

        Game game = new Game();
        game.setName("Partida 1");
        Deck deck = deckService.init(game.getName());
        game.setCode("AAAABBBB1");
        game.setPrivacity(PRIVACITY.PUBLIC);
        game.setHas_started(false);
        game.setPlayer(playerService.findPlayerById(1).get());
        game.setDeck(deck);

        
        gameService.save(game);

        gameService.delete(game);

        int countAfter = gameService.gameCount();

        assertEquals(countBefore, countAfter);

    }

     
    @Test
    public void testFindGamesWhereIPlayedByPlayedId(){  // HU7 counts the games where the player played
        Iterable<Game> games = gameService.findGamesByPlayerId(1);
        long count = games.spliterator().getExactSizeIfKnown();
        assertEquals(5, count);
    }

    
}

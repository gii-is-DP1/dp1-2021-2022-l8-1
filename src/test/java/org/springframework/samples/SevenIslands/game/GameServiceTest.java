package org.springframework.samples.SevenIslands.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTest {
    
    @Autowired
    private GameService gameService;
    
    @Test
    public void testCountWithInitialData(){
        int count = gameService.gameCount();
        assertEquals(5, count);
    }
    
    @Test
    public void testFindGamesByPlayerId(){  // counts the games where the player as created
        Iterable<Game> games = gameService.findGamesByPlayerId(1);
        long count = games.spliterator().getExactSizeIfKnown();
        assertEquals(4, count);
    }

     
    @Test
    public void testFindGamesWherePlayerPlayed(){  // counts the games where the player played
        Iterable<Game> games = gameService.findGamesWhereIPlayerByPlayerId(1);
        long count = games.spliterator().getExactSizeIfKnown();
        assertEquals(4, count);
    }
    
}

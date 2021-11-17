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
        assertEquals(3, count);
    }
    
    @Test
    public void testFindGamesByPlayerId(){  // counts the games of a player
        Iterable<Game> games = gameService.findGamesByPlayerId(2);
        long count = games.spliterator().getExactSizeIfKnown();
        assertEquals(2, count);
    }
    
}

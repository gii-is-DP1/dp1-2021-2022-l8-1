package org.springframework.samples.SevenIslands.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;
    
    @Test
    public void testCountWithInitialData(){
        int count = playerService.playerCount();
        assertEquals(3, count);
    }

    @Test
    public void testFindPlayersByGameId() {
        Iterable<Player> players = playerService.findPlayersByGameId(1);//JUGADORES QUE HAY EN UNA PARTIDA DADA POR EL ID
        long count = players.spliterator().getExactSizeIfKnown();
        assertEquals(2, count);
    }

    @Test
    public void testFindInvitationsByPlayerId(){
        Iterable<Player> players = playerService.findInvitationsByPlayerId(2);
        long count = players.spliterator().getExactSizeIfKnown();
        assertEquals(2, count);
    }

    @Test
    public void testFindRequestsByPlayerId(){
        Iterable<Player> players = playerService.findRequestsByPlayerId(3);
        long count = players.spliterator().getExactSizeIfKnown();
        assertEquals(1, count);

    }

    @Test
    public void testWatchGameByPlayerId(){
        Iterable<Player> players = playerService.findWatchGameByPlayerId(3);
        long count = players.spliterator().getExactSizeIfKnown();
        assertEquals(1, count);
    }

    @Test
    public void testFindByForumId(){
        Iterable<Player> players = playerService.findByForumId(1);
        assertEquals(1, players.spliterator().getExactSizeIfKnown());
    }
}

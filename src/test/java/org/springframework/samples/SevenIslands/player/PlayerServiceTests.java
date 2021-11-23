package org.springframework.samples.SevenIslands.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {

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

    
    @Test
    public void testCountAllPlayers() { //HISTORIA DE USUARIO H16 CASO DE USO POSITIVO
        long count = playerService.findAll().spliterator().getExactSizeIfKnown();
        assertEquals(count, 3);
    }

    @Test
    public void testCountPlayerWithEspecificWordInUsername() { //HISTORIA DE USUARIO H17 CASO DE USO POSITIVO 2
        Iterable<Player> a = playerService.findAll();
        long n = StreamSupport.stream(a.spliterator(), false).filter(x->x.getUser().getUsername().contains("test")).count();
        assertEquals(n, 3);
    }

    @Test
    public void testUpdateUserFirstName(){ //HISTORIA DE USUARIO H17 CASO DE USO POSITIVO 3
        Player p = playerService.findPlayerById(1).get();
        String n = p.getFirstName();
        
        p.setFirstName("Beatriz");
        
        
        assertNotEquals(n,p.getFirstName());
       
    }

    @Test
    public void testUpdateUserSurName(){ //HISTORIA DE USUARIO H17 CASO DE USO POSITIVO 3
        Player p = playerService.findPlayerById(1).get();
        String m = p.getSurname();
        p.setSurname("Diaz Salgado");
        
        
        assertNotEquals(m,p.getSurname());
       
    }

    // @Test
    // public void testUpdateUsername(){ //HISTORIA DE USUARIO H17 CASO DE USO NEGATIVO 2
    //     try{
    //         Player p = playerService.findPlayerById(1).get();
    //         p.getUser().setUsername("test2");
            
    //     }
    //     catch(Exception e){
           
    //     }
        
        
       
    // }

    // @Test
    // public void deleteUser(){ //HISTORIA DE USUARIO H17 CASO DE USO POSITIVO 3
    //     Player p = playerService.findPlayerById(1).get();
    //     playerService.delete(p);
        
    //     assertTrue(playerService.findPlayerById(1)==null);
       
    // }
}

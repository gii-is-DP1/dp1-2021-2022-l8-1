package org.springframework.samples.SevenIslands.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;
    
    @Test
    public void testCountWithInitialData(){
        int count = playerService.playerCount();
        assertEquals(3, count);
    }

    @Test
    public void shouldInsertPlayerAndUser(){

        Player player = new Player();
        player.setFirstName("Antonio");
        player.setSurname("García");
        player.setEmail("antoniogar@gmail.com");
        player.setProfilePhoto("www.foto.png");

        User user = new User();
        user.setUsername("antoniog11");
        user.setPassword("4G4rc14");
        user.setEnabled(true);

        player.setUser(user);
    
        playerService.savePlayer(player);

        assertThat(player.getId().longValue()).isNotEqualTo(0);


    }

    /*@Test
    public void shouldDeletePlayer(){

        Player player = new Player();
        player.setFirstName("Antonio");
        player.setSurname("García");
        player.setEmail("antoniogar@gmail.com");
        player.setProfilePhoto("www.foto.png");

        User user = new User();
        user.setUsername("antoniog11");
        user.setPassword("4G4rc14");
        user.setEnabled(true);

        player.setUser(user);
    
        //Similar to .savePlayer(player)
        playerService.save(player);
        userService.saveUser(player.getUser());
        authoritiesService.saveAuthorities(player.getUser().getUsername(), "player");

        int countBefore = playerService.playerCount();
        
        playerService.delete(player);

        int countAfter = playerService.playerCount();

        assertNotEquals(countBefore, countAfter);

    }*/

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
    public void testFinPlayerByUsername(){
        int count = playerService.getPlayerByName("test2").size();
        assertEquals(1,count);
    }

    @Test
    public void testGetIdlayerByUsername(){
        int playerId = playerService.getIdPlayerByName("test3");
        assertEquals(3,playerId);
    }

    @Test
    public void testFindPlayerBySurname(){
        int count = playerService.findPlayerBySurname("Alonso").size();
        assertEquals(1,count);
    }

    @Test
    public void testGetAchievementsByPlayerId(){
        long count = playerService.getAchievementsByPlayerId(1).spliterator().getExactSizeIfKnown();
        assertEquals(2,count);
    }

    @Test
    public void testGetCardsByPlayerId(){
        long count = playerService.getCardsByPlayerId(1).spliterator().getExactSizeIfKnown();
        assertEquals(3,count);
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

    //Historias de usuario

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

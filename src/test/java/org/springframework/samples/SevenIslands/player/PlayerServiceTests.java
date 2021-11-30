package org.springframework.samples.SevenIslands.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.user.Authorities;
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

        Player player1 = new Player();
        player1.setFirstName("Antonio1");
        player1.setSurname("García1");
        player1.setEmail("antonio1gar@gmail.com");
        player1.setProfilePhoto("www.foto.png");

        User user = new User();
        user.setUsername("antoniog11");
        user.setPassword("4G4rc14");
        user.setEnabled(true);

        User user1 = new User();
        user1.setUsername("antoniog111");
        user1.setPassword("4G4rc14");
        user1.setEnabled(true);

        player.setUser(user);
        player1.setUser(user1);
    
        
        playerService.save(player);
        playerService.savePlayer(player1);

        Player p  = playerService.findPlayerById(4).get();
        Player p2  = playerService.findPlayerById(5).get();

        assertThat(p.getFirstName().equals("Antonio"));
        assertThat(p2.getFirstName().equals("Antonio1"));
        assertThat(player.getId().longValue()).isNotEqualTo(0);


    }

    @Test
    public void shouldDeletePlayer(){

        int playerId=1;

        int countBefore=playerService.playerCount();

        Optional<Player> player = playerService.findPlayerById(playerId);
        if(player.isPresent()){
            Player p = player.get();
            Collection<Game> lg = p.getGames();
            Collection<Game> col = lg.stream().filter(x->x.getPlayer().getId()!=playerId).collect(Collectors.toCollection(ArrayList::new));
            col.stream().forEach(x->x.deletePlayerOfGame(p));

            p.deleteGames(col);
    
            playerService.delete(player.get());
        }

        int countAfter=playerService.playerCount();


        assertNotEquals(countBefore, countAfter);

    }

    @Test
    public void testFindPlayersByGameId() {
        Iterable<Player> players = playerService.findPlayersByGameId(1);//JUGADORES QUE HAY EN UNA PARTIDA DADA POR EL ID
        long count = players.spliterator().getExactSizeIfKnown();
        assertEquals(2, count);
    }

    // @Test
    // public void testFindInvitationsByPlayerId(){
    //     Iterable<Player> players = playerService.findInvitationsByPlayerId(2);
    //     long count = players.spliterator().getExactSizeIfKnown();
    //     assertEquals(2, count);
    // }

    // @Test
    // public void testFindRequestsByPlayerId(){
    //     Iterable<Player> players = playerService.findRequestsByPlayerId(3);
    //     long count = players.spliterator().getExactSizeIfKnown();
    //     assertEquals(1, count);

    // }

    @Test
    public void testFinPlayerByUsername(){
        int count = playerService.getPlayerByUsername("test2").size();
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

    //USER´S HISTORIES

    @Test
    public void testCountAllPlayers() { // H16 - POSITIVE 1
        long count = playerService.findAll().spliterator().getExactSizeIfKnown();
        assertEquals(count, 3);
    }

    @Test
    public void testCountPlayerWithEspecificWordInUsername() { //H17 -POSITIVE 2
        Iterable<Player> a = playerService.findAll();
        long n = StreamSupport.stream(a.spliterator(), false).filter(x->x.getUser().getUsername().contains("test")).count();
        assertEquals(n, 3);
    }

    @Test
    public void testUpdateUserFirstName(){ //H17 - POSITIVE 3
        Player p = playerService.findPlayerById(1).get();
        String n = p.getFirstName();
        
        p.setFirstName("Beatriz");
        
        
        assertNotEquals(n,p.getFirstName());
       
    }

    @Test
    public void testUpdateUserSurName(){ //H17 - POSITIVE 3
        Player p = playerService.findPlayerById(1).get();
        String m = p.getSurname();
        p.setSurname("Diaz Salgado");
        
        
        assertNotEquals(m,p.getSurname());
       
    }


    @Test
    public void shouldDeleteUser(){

        User user = new User();
        user.setUsername("antoniog11");
        user.setPassword("4G4rc14");
        user.setEnabled(true);

        userService.saveUser(user);

        int countBefore = userService.userCount();

        userService.delete(user.getUsername());

        int countAfter = userService.userCount();

    
        assertNotEquals(countBefore, countAfter);

    }

    
    @Test
    public void testAuthorities(){

        long countBefore = authoritiesService.count();

        Authorities a = new Authorities();
        a.setAuthority("player");
        authoritiesService.saveAuthorities(a);

       

        authoritiesService.deleteAuthorities(a.getId());
        long countAfter = authoritiesService.count();

        assertEquals(countBefore, countAfter);

    }

    @Test
    public void testAuthorities2(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authoritiesService.saveAuthorities("pepito", "player");
        });
    
        String expectedMessage = "pepito not found";
        String actualMessage = exception.getMessage();
    
        assertThat(actualMessage.contains(expectedMessage));
        
    }

}



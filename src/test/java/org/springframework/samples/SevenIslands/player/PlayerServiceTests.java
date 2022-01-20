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
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.samples.SevenIslands.achievement.Achievement;
import org.springframework.samples.SevenIslands.achievement.AchievementService;
import org.springframework.samples.SevenIslands.admin.Admin;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.statistic.Statistic;
import org.springframework.samples.SevenIslands.user.Authorities;
import org.springframework.samples.SevenIslands.user.AuthoritiesService;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.samples.SevenIslands.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class PlayerServiceTests {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthoritiesService authoritiesService;

    @Autowired 
    private AchievementService achievementService;

    @Autowired
    private AdminService adminService;


    
    @Test
    void testCountWithInitialData(){
        int count = playerService.playerCount();
        assertEquals(11, count);
    }

    @Test
    void shouldInsertPlayerAndUser(){

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

        Set<Statistic> statistic = new HashSet<>();
        player1.setStatistic(statistic);

        User user = new User();
        user.setUsername("antoniog11");
        user.setPassword("4G4rc14!1234");
        user.setEnabled(true);

        User user1 = new User();
        user1.setUsername("antoniog111");
        user1.setPassword("4G4rc14!1234");
        user1.setEnabled(true);

        player.setUser(user);
        player1.setUser(user1);
    
        
        playerService.save(player);
        playerService.savePlayer(player1);

        Player p  = playerService.findPlayerById(12).get();
        Player p2  = playerService.findPlayerById(13).get();

        assertThat(p.getFirstName().equals("Antonio"));
        assertThat(p2.getFirstName().equals("Antonio1"));
        assertThat(player.getId().longValue()).isNotZero();


    }

    @Test
    void shouldDeletePlayer(){

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


        assertEquals(countBefore-1, countAfter);

    }

    @Test
    void testFindPlayersByGameId() {
        Iterable<Player> players = playerService.findPlayersByGameId(1);//JUGADORES QUE HAY EN UNA PARTIDA DADA POR EL ID
        long count = players.spliterator().getExactSizeIfKnown();
        assertEquals(2, count);
    }

    @Test
    void testFinPlayerByUsername(){
        int count = playerService.getPlayerByUsername("test2").size();
        assertEquals(1,count);
    }

    @Test
    void testGetIdlayerByUsername(){
        int playerId = playerService.getIdPlayerByName("test3");
        assertEquals(3,playerId);
    }

    @Test
    void testFindPlayerBySurname(){
        int count = playerService.findPlayerBySurname("Alonso").size();
        assertEquals(1,count);
    }

    @Test
    void testGetAchievementsByPlayerId(){
        long count = playerService.getAchievementsByPlayerId(1).spliterator().getExactSizeIfKnown();
        assertEquals(2,count);
    }

    @Test
    void testCountPlayersPageable() {
        Pageable page = PageRequest.of(0, 5);
        Iterable<Player> players = playerService.findAll(page);
        List<Player> playersLs = StreamSupport.stream(players.spliterator(), false).collect(Collectors.toList());
        long pageSize = playersLs.size();
        assertEquals(5, pageSize);
        
        
    }

    @Test
    void testFindIfPlayerContains() {
        Pageable page = PageRequest.of(0, 5);
        String data = "paco";
        Page<Player> players = playerService.findIfPlayerContains(data, page);
        assertEquals(1, players.getTotalElements());
    }

    @Test
    void testPlayerHasInappropiateWordsInFirstName() {
        Player player = new Player();
        player.setFirstName("Fucking");
        player.setSurname("Fernández");
        player.setEmail("antoniogar2@gmail.com");
        player.setProfilePhoto("www.foto.png");

        User user = new User();
        user.setUsername("Antoniof22");
        user.setPassword("4G4rc14!1234");
        user.setEnabled(true);

        player.setUser(user);
    
        playerService.savePlayer(player);

        boolean b = playerService.playerHasInappropiateWords(player);
        assertEquals(true, b);


    }

    @Test
    void testPlayerHasInappropiateWordsInSurname() {
        Player player = new Player();
        player.setFirstName("Adrián");
        player.setSurname("FucKing");
        player.setEmail("adrif2@gmail.com");
        player.setProfilePhoto("www.foto.png");

        User user = new User();
        user.setUsername("adrif2");
        user.setPassword("4G4rc14!1234");
        user.setEnabled(true);

        player.setUser(user);
    
        playerService.savePlayer(player);

        boolean b = playerService.playerHasInappropiateWords(player);
        assertEquals(true, b);


    }

    @Test
    void testPlayerHasInappropiateWordsInUserName() {
        Player player = new Player();
        player.setFirstName("Adrián");
        player.setSurname("Fernández");
        player.setEmail("adrif2@gmail.com");
        player.setProfilePhoto("www.foto.png");

        User user = new User();
        user.setUsername("fuckingAdrianf22");
        user.setPassword("4G4rc14!1234");
        user.setEnabled(true);

        player.setUser(user);
    
        playerService.savePlayer(player);

        boolean b = playerService.playerHasInappropiateWords(player);
        assertEquals(true, b);

    }

    @Test
    void testCalculatePages() {
        List<Integer> pages = playerService.calculatePages(2);

        //pages[0] es la previousPage, pages[1] es la nextPage
        // Dado que pageNumber es 2 == totalPages-1 (página final), pages[0] debe ser pageNumber-1, y pages[1] debe ser pageNumber 

        assertEquals(1, pages.get(0));
        assertEquals(2, pages.get(1));
    }

    @Test
    void testCalculatePages2() {
        List<Integer> pages = playerService.calculatePages(1);

        //pages[0] es la previousPage, pages[1] es la nextPage
        // Dado que pageNumber es 1 (página intermedia)  pages[0] debe ser pageNumber-1, y pages[1] debe ser pageNumber +1

        assertEquals(0, pages.get(0));
        assertEquals(2, pages.get(1));
    }

    @Test
    void testCalculatePages3() {
        List<Integer> pages = playerService.calculatePages(0);
        List<Integer> pages2 = playerService.calculatePages(null);

        //pages[0] es la previousPage, pages[1] es la nextPage
        // Dado que pageNumber es 1 (primera página)  pages[0] debe ser pageNumber, y pages[1] debe ser pageNumber +1

        assertEquals(0, pages.get(0));
        assertEquals(1, pages.get(1));

        assertEquals(0, pages2.get(0));
        assertEquals(1, pages2.get(1));

    }

    @Test
    void testGetAchievements() {
        
        Player player = new Player();
        player.setFirstName("Adrián");
        player.setSurname("Fernández");
        player.setEmail("adrif2@gmail.com");
        player.setProfilePhoto("www.foto.png");

        User user = new User();
        user.setUsername("Adrianf22");
        user.setPassword("4G4rc14!1234");
        user.setEnabled(true);

        player.setUser(user);
    
        playerService.savePlayer(player);

        List<Achievement> achievements = StreamSupport.stream(achievementService.findAll().spliterator(), false).collect(Collectors.toList());
        Set<Achievement> set = new HashSet<>();
        set.add(achievements.get(0));
        set.add(achievements.get(1));
        player.setAchievements(set);
        
        List<Achievement> achieved = StreamSupport.stream(playerService.getAchievementsByPlayerId(player.getId()).spliterator(), false).collect(Collectors.toList());
        List<Achievement> notAchieved = achievements.stream().filter(x->!achieved.contains(x)).collect(Collectors.toList());

        Set<Statistic> s = new HashSet<>();
        Statistic stat = new Statistic();
        stat.setPoints(120);

        s.add(stat);
        player.setStatistic(s);
        stat.setPlayer(player);
        

        List<Achievement> finalAchieved = playerService.getAchievements(notAchieved, achieved, player);

        assertEquals(3, finalAchieved.size());
    }

    
    @Test
    void testGetPaginatedPlayers() {
        String filterName = "";
        String filterName2 = "alonso";
        Integer pageNumber = 1;

        List<Player> players = playerService.getPaginatedPlayers(filterName, pageNumber);
        List<Player> players2 = playerService.getPaginatedPlayers(filterName2, null);

        assertEquals(5, players.size());
        assertEquals(1, players2.size());


    }

    @Test
    void testProcessEditPlayer() {

        Player p = playerService.findAll().iterator().next();   // player a editar

        Player player2 = new Player();
        player2.setFirstName(p.getFirstName());
        player2.setSurname(p.getSurname());
        player2.setEmail(p.getEmail());
        player2.setId(p.getId());
        player2.setProfilePhoto(p.getProfilePhoto());

        User user2 = new User();
        user2.setUsername(p.getUser().getUsername() + "1"); // test1 now is test11 (already in DB)
        user2.setPassword(p.getUser().getPassword());
        user2.setEnabled(p.getUser().isEnabled());

        player2.setUser(user2);

        

        BindingResult result = new BeanPropertyBindingResult(player2, "player");

        String res = playerService.processEditPlayer(player2, p.getId(), result);
        assertEquals("errors/error-500", res);

    }

    @Test
    void testProcessEditPlayer2(){

        Player p = playerService.findAll().iterator().next();   // player a editar
        p.setEmail("test1@us.es");

        Player player2 = new Player();
        player2.setFirstName(p.getFirstName());
        player2.setSurname(p.getSurname());
        player2.setEmail(p.getEmail());
        player2.setId(p.getId());
        player2.setProfilePhoto(p.getProfilePhoto());

        User user2 = new User();
        user2.setUsername(p.getUser().getUsername() + "999"); // test1 now is test1999 (not in DB)
        user2.setPassword(p.getUser().getPassword());
        user2.setEnabled(p.getUser().isEnabled());

        player2.setUser(user2);

        Authentication auth = new UsernamePasswordAuthenticationToken(p.getUser().getUsername(), p.getUser().getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);

        BindingResult result = new BeanPropertyBindingResult(player2, "player");

        String res = playerService.processEditPlayer(player2, p.getId(), result);
        assertEquals("redirect:/welcome", res);


    }



    @Test
    void testProcessEditPlayer3(){

        Player p = playerService.findAll().iterator().next();   // player a editar
        p.setEmail("test1@us.es");

        Player player2 = new Player();
        player2.setFirstName(p.getFirstName());
        player2.setSurname(p.getSurname());
        player2.setEmail(p.getEmail());
        player2.setId(p.getId());
        player2.setProfilePhoto(p.getProfilePhoto());

        User user2 = new User();
        user2.setUsername(p.getUser().getUsername() + "999"); // test1 now is test1999 (not in DB)
        user2.setPassword(p.getUser().getPassword());
        user2.setEnabled(p.getUser().isEnabled());

        player2.setUser(user2);

        Admin ad = adminService.getAdminByName("mra21").get();


        Set<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(ad.getUser().getAuthorities().iterator().next().getAuthority());
        authorities.add(authority);

        Authentication auth = new UsernamePasswordAuthenticationToken(ad.getUser().getUsername(), ad.getUser().getPassword(),  authorities);


        SecurityContextHolder.getContext().setAuthentication(auth);
        
        BindingResult result = new BeanPropertyBindingResult(player2, "player");

        String res = playerService.processEditPlayer(player2, p.getId(), result);
        String res2 = playerService.processEditPlayer(player2, 400, result);    // if player is not present
        assertEquals("redirect:/players", res);
        assertEquals("redirect:/players", res2);    // bc i'm an admin


    }

    @Test
    void testProcessEditPlayer4(){

        Player p = playerService.findAll().iterator().next();   // player a editar
        p.setEmail("test1@us.es");

        Player player2 = new Player();
        player2.setFirstName(p.getFirstName());
        player2.setSurname(p.getSurname());
        player2.setEmail(p.getEmail());
        player2.setId(p.getId());
        player2.setProfilePhoto(p.getProfilePhoto());

        User user2 = new User();
        user2.setUsername(p.getUser().getUsername() + "999"); // test1 now is test1999 (not in DB)
        user2.setPassword(p.getUser().getPassword());
        user2.setEnabled(p.getUser().isEnabled());

        player2.setUser(user2);

        Authentication auth = new UsernamePasswordAuthenticationToken(p.getUser().getUsername(), p.getUser().getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        BindingResult result = new BeanPropertyBindingResult(player2, "player");

        String res = playerService.processEditPlayer(player2, 400, result); // if player is not present
        assertEquals("errors/error-404", res);


    }

    //USER´S HISTORIES


    @Test
    void testCountAllPlayers() { // H16 - POSITIVE 1
        long count = playerService.findAll().spliterator().getExactSizeIfKnown();
        assertEquals(11, count);
    }

    @Test
    void testCountPlayerWithEspecificWordInUsername() { //H17 -POSITIVE 2
        Iterable<Player> a = playerService.findAll();
        long n = StreamSupport.stream(a.spliterator(), false).filter(x->x.getUser().getUsername().contains("1")).count();
        assertEquals(3, n);
    }

    @Test
    void testUpdateUserFirstName(){ //H17 - POSITIVE 3
        Player p = playerService.findPlayerById(1).get();
        String n = p.getFirstName();
        
        p.setFirstName("Beatriz");
        
        
        assertNotEquals(n,p.getFirstName());
       
    }

    @Test
    void testUpdateUserSurName(){ //H17 - POSITIVE 3
        Player p = playerService.findPlayerById(1).get();
        String m = p.getSurname();
        p.setSurname("Diaz Salgado");
        
        
        assertNotEquals(m,p.getSurname());
       
    }


    @Test
    void shouldDeleteUser(){

        int countBefore = userService.userCount();

        User user = new User();
        user.setUsername("antoniog11");
        user.setPassword("4G4rc14!1234");
        user.setEnabled(true);

        userService.saveUser(user);

        userService.delete(user.getUsername());

        int countAfter = userService.userCount();

    
        assertEquals(countBefore, countAfter);

    }

    
    @Test
    void testAuthorities(){

        long countBefore = authoritiesService.count();

        Authorities a = new Authorities();
        a.setAuthority("player");
        authoritiesService.saveAuthorities(a);

       

        authoritiesService.deleteAuthorities(a.getId());
        long countAfter = authoritiesService.count();

        assertEquals(countBefore, countAfter);

    }

    @Test
    void testAuthorities2(){

        Exception exception = assertThrows(RuntimeException.class, () -> {
            authoritiesService.saveAuthorities("pepito", "player");
        });
    
        String expectedMessage = "pepito not found";
        String actualMessage = exception.getMessage();
    
        assertThat(actualMessage.contains(expectedMessage));
        
    }


}



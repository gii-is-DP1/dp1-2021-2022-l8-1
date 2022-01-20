package org.springframework.samples.SevenIslands.board;

import static org.junit.Assert.assertFalse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.card.CARD_TYPE;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.card.CardService;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckService;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.game.PRIVACITY;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.statistic.Statistic;
import org.springframework.samples.SevenIslands.user.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.junit.jupiter.api.Disabled;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class BoardServiceTests {

    private static final int TEST_GAME_ID = 10;
    private static final int TEST_PLAYER_ID = 10;
    private static final int TEST_PLAYER_ID_2 = 11;

    @Autowired
    private BoardService boardService;
    

    @Autowired
    private DeckService deckService;

    @Autowired
    private CardService cardService;

    @MockBean
    private PlayerService playerService;

    @MockBean
    private GameService gameService;

    @BeforeEach
    void setup(){
        Player firstPlayer = new Player();
        firstPlayer.setId(TEST_PLAYER_ID);
        firstPlayer.setProfilePhoto("https://imagen.png");
        firstPlayer.setFirstName("Manuel");
        firstPlayer.setSurname("González");
        firstPlayer.setEmail("manuelgonzalez@gmail.com");
        Card card1 = cardService.findCardById(1).get();
        Card card2 = cardService.findCardById(28).get();
        List<Card> listCards = new ArrayList<>();
        listCards.add(card1);
        listCards.add(card2);
        firstPlayer.setCards(listCards);

        Set<Statistic> statistic = new HashSet<>();
        firstPlayer.setStatistic(statistic);

        Player secondPlayer = new Player();
        secondPlayer.setId(TEST_PLAYER_ID_2);
        secondPlayer.setProfilePhoto("https://imagen.png");
        secondPlayer.setFirstName("Manuel");
        secondPlayer.setSurname("González");
        secondPlayer.setEmail("manuelgonzalez@gmail.com");
        List<Card> listCards2 = new ArrayList<>();
        secondPlayer.setCards(listCards2);

        Set<Statistic> statistic2 = new HashSet<>();
        secondPlayer.setStatistic(statistic2);


        User user = new User();
        user.setUsername("antoniog11");
        user.setPassword("4G4rc14!1234");
        user.setEnabled(true);
        firstPlayer.setUser(user);


    
        Game game = new Game();
        game.setId(TEST_GAME_ID);
        game.setName("Partida 1");
        Deck deck = deckService.init(game.getName());
       
        game.setCode("AAAABBBB1");
        game.setActualPlayer(1);
        game.setPrivacity(PRIVACITY.PUBLIC);
        game.setHasStarted(false);
        game.setPlayer(firstPlayer);
        game.setBoard(boardService.findById(1).get());
        List<Player> listPlayer = new ArrayList<>();
        listPlayer.add(firstPlayer);
        game.setPlayers(listPlayer);
        game.setDeck(deck);



        when(this.gameService.findGameById(TEST_GAME_ID)).thenReturn(Optional.of(game));
        when(this.playerService.findPlayerById(TEST_PLAYER_ID)).thenReturn(Optional.of(firstPlayer));
        when(this.playerService.findPlayerById(TEST_PLAYER_ID_2)).thenReturn(Optional.of(secondPlayer));

        List<Game> listGames = new ArrayList<>();
        listGames.add(game);
        Iterator<Game> iterator = listGames.iterator();
        Iterable<Game>iterable = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0),false).collect(Collectors.toList());

        when(gameService.findGamesByRoomCode(game.getCode())).thenReturn(iterable);

    }

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
    public void shouldInsertBoard(){
        List<Island> islands = new ArrayList<>();
        Board board = new BoardBuilder().setIslands(islands).build();

        boardService.save(board);

        assertThat(board.getId().longValue()).isNotEqualTo(0);

        assertEquals(boardService.findById(board.getId()).get(), board);
    }

    @WithMockUser(value = "spring")
    @Test
    public void testInit(){
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        boardService.init(game);
        assertThat(game.getBoard().getIslands()).isNotEmpty();
    }

    @Disabled
    @WithMockUser(value = "spring")
    @Test
    public void testInitBoard(){
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        boardService.initBoard(game);

        assertThat(game.getBoard().getIslands().stream().allMatch(x->x.getCard() != null));
    }
    

    @WithMockUser(value = "spring")
    @Test
    public void testFinishTheGame(){
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        LocalDateTime notEndedTime = game.getEndTime();
        boardService.finishTheGame(game);
        

        assertThat(notEndedTime != game.getEndTime());
    }

    @WithMockUser(value = "spring")
    @Test
    public void testRollDie(){
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        Boolean throwed = game.getDieThrows();
        boardService.rollDie(game);

        assertThat(throwed != game.getDieThrows());
    }

    
    @WithMockUser(value = "spring")
    @Test
    public void testChangeTurn(){
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        boardService.changeTurn(game);
        assertThat(game.getActualPlayer() == 2);
    }

    @WithMockUser(value = "spring")
    @Test
    public void testChangeDeckZeroCards(){
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        boardService.changeDeckZeroCards(game, game.getDeck().getCards());
        assertThat(game.getDeck().getCards().size() == 0);
    }

    @WithMockUser(value = "spring")
    @Test
    public void testDistribute(){
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        boardService.init(game);
        Board board = game.getBoard();
        Deck deck = game.getDeck();
        boardService.distribute(board, deck);
        assertTrue(boardService.findById(board.getId()).get().getIslands()
        .stream().noneMatch(x-> x.getCard()==null));
    }
    @Disabled
    @WithMockUser(value = "spring")
    @Test
    public void testInitCardPlayers(){
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        boardService.initCardPlayers(game);
        Player player = game.getPlayers().stream().findFirst().get();
        assertEquals(player.getCards().stream()
            .filter(x-> x.getCardType().equals(CARD_TYPE.DOUBLON)).collect(Collectors.toList()).size(), 3);
    }

    @WithMockUser(value = "spring")
    @Test
    public void testCalcPlayersByPunctuation(){
        List<Player> listPlayerAtStart = new ArrayList<>();
        Player p1 = playerService.findPlayerById(TEST_PLAYER_ID).get();
        Player p2 = playerService.findPlayerById(TEST_PLAYER_ID_2).get();
        listPlayerAtStart.add(p1);
        listPlayerAtStart.add(p2);

        List<Player> listPlayers = new ArrayList<>();
        listPlayers.add(p1);

        LinkedHashMap<Player, Integer> res = boardService.calcPlayersByPunctuation(listPlayerAtStart, listPlayers);
        assertEquals(res.get(p2), 0);
        assertEquals(res.get(p1), 2);


    }

    @Test
    public void testLeaveGame(){ 
        Game game = gameService.findGameById(TEST_GAME_ID).get();
        Player player1 = playerService.findPlayerById(TEST_PLAYER_ID).get();
        Player player2 = playerService.findPlayerById(TEST_PLAYER_ID_2).get();
        game.setPlayers(List.of(player1, player2));   
        List<Player> players = game.getPlayers();
        String code = game.getCode();
        if(players.stream().anyMatch(x->x.equals(player1))){
            boardService.leaveGame(player1, code);
            List<Player> actualPlayers = game.getPlayers();
            assertTrue(actualPlayers.stream().noneMatch(x->x.equals(player1)));
        }
    }

    
}

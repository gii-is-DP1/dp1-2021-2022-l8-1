package org.springframework.samples.SevenIslands.board;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.admin.Admin;
import org.springframework.samples.SevenIslands.admin.AdminService;
import org.springframework.samples.SevenIslands.card.CARD_TYPE;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckService;
import org.springframework.samples.SevenIslands.die.Die;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.statistic.Statistic;
import org.springframework.samples.SevenIslands.statistic.StatisticService;
import org.springframework.security.core.userdetails.User;
import org.springframework.samples.SevenIslands.util.Pair;
import org.springframework.samples.SevenIslands.util.SecurityService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private DeckService deckService;

    @Autowired
    private IslandService islandService;

    @Autowired
    private GameService gameService;
    
    @Autowired
    private PlayerService playerService;
 
    @Autowired	
	private StatisticService statisticService;

    @Autowired	
	private SecurityService securityService;

    @Autowired	
	private AdminService adminService;

    private static final String REDIRECT_TO_BOARDS = "redirect:/boards/";
    private static final String MESSAGE = "message";
    private static final String OPTIONS = "options";
    private static final String PLAYER = "player";
    private static final String PLAYER_AT_START = "playersAtStart";

    @Transactional
    public int boardCount(){
        return (int) boardRepo.count();
    }
    
    public Optional<Board> findById(Integer id){
        return boardRepo.findById(id);
    }

    @Transactional
    public void save(Board b){
        boardRepo.save(b);
    }

    @Transactional
    public String initBoard(Game g){        //Creating 6 islands and a board, placing 1 card from the deck on each island
                                            // and handing out 3 doubloons to each player at the beginning of the game
        Board board = g.getBoard();
        
        initCardPlayers(g);
        distribute(board, g.getDeck());
        g.setBoard(board);
        gameService.save(g);     
        
        return REDIRECT_TO_BOARDS+ g.getCode();
    }

    @Transactional
    public void init(Game game) {  //Creating 6 islands and a board
        List<Island> l = new ArrayList<>();
        for(int i=1;i<7;i++){
            Island isl = new Island();          
            isl.setIslandNum(i);
            islandService.save(isl);
            l.add(isl);
        }
        Board board = new BoardBuilder().setIslands(l).build();
        game.setBoard(board);
        gameService.save(game);
        boardRepo.save(board);
        
    }

    @Transactional
    public void distribute(Board b, Deck d){        //Placing 1 card from the deck on each island
        
        if(!d.getCards().isEmpty() && d.getCards().size()>=6){              
            for(Island i: b.getIslands()){
                if(i.getCard()==null){  
                    List<Card> cards =  d.getCards();
                    Card c= null;
                    Optional<Card> cardOp = cards.stream().findFirst();
                    if(cardOp.isPresent()){
                        c = cardOp.get(); 
                    }    
                    if(c!=null){
                        d.getCards().remove(c);
                        i.setCard(c);   
                    }
                        
                }
            }
            
            deckService.save(d);
            boardRepo.save(b);

        }

    }

    @Transactional
    public void initCardPlayers(Game game){             //Handing out 3 doubloons to each player at the beginning of the game
        List<Player> players = game.getPlayers();       
        Deck d = game.getDeck();
        for(Player p: players){
            List<Card> cards = new ArrayList<>();      
            List<Card> doblones = d.getCards().stream().filter(x->x.getCardType().equals(CARD_TYPE.DOUBLON)).limit(3).collect(Collectors.toList());
            d.getCards().removeAll(doblones);
            deckService.save(d);
            cards.addAll(doblones);
            p.setCards(cards);
            Statistic s = new Statistic();
            s.setPlayer(p);

            statisticService.save(s);

            statisticService.insertinitIslandCount(s.getId(),1);
            statisticService.insertinitIslandCount(s.getId(),2);
            statisticService.insertinitIslandCount(s.getId(),3);
            statisticService.insertinitIslandCount(s.getId(),4);
            statisticService.insertinitIslandCount(s.getId(),5);
            statisticService.insertinitIslandCount(s.getId(),6);
            p.getStatistic().add(s);
                  
            playerService.save(p);
        }
    }

    @Transactional
    public String gameLogic(Player pl, Game game, ModelMap modelMap, HttpServletRequest request){
        
        String res=null;

        if(!game.isHasStarted()){                       //The game has not started
            
            gameIsNotStarted(game, request);

        
        //The condition for the game to end is met
        }else if(game.getPlayers().stream().filter(x->x.getInGame()).count()==1L || game.getActualPlayer()==0 && game.getDeck().getCards().isEmpty()){                     

            return finishTheGame(game);

        }else if(pl != game.getPlayers().get(game.getActualPlayer())){         //so that even if it refreshes one that is not its turn, it does not increase the turn
           
            request.getSession().removeAttribute(MESSAGE);
            request.getSession().removeAttribute(OPTIONS);
            
        }else if(ChronoUnit.SECONDS.between(game.getTurnTime(), LocalDateTime.now())>=40){      //If the 40 seconds of the turn time elapse
       
            Integer n = game.getPlayers().size();
            game.setActualPlayer((game.getActualPlayer()+1)%n);
            game.setDieThrows(false);       
            game.setTurnTime(LocalDateTime.now());
            request.getSession().removeAttribute(MESSAGE);
            request.getSession().removeAttribute(OPTIONS);

        }

        gameService.save(game);
        modelMap.addAttribute("id_playing", game.getPlayers().get(game.getActualPlayer()).getId());
        Long temp = ChronoUnit.SECONDS.between(game.getTurnTime(), LocalDateTime.now());
        modelMap.addAttribute("tempo", 40-temp.intValue());

        return res;

    }
    @Transactional
    public void gameIsNotStarted(Game game, HttpServletRequest request){
        List<Player> players = game.getPlayers();
        List<Integer> playersAtStart = new ArrayList<>();
        players.forEach(p -> {
            p.setInGame(true);                  //Player now is in game
            playerService.save(p);
            playersAtStart.add(p.getId());
        });    
        Collections.shuffle(players);           //We randomly determine the turn of the players
        game.setPlayers(players);
        
        game.setActualPlayer(0);                //The first player is the one in position 0
        game.setHasStarted(true);
        game.setTurnTime(LocalDateTime.now());  //Start the turn of the firstplayer

        request.getSession().setAttribute(PLAYER_AT_START, playersAtStart);
    }

    @Transactional
    public String finishTheGame(Game game){
        
        game.setEndTime(LocalDateTime.now());
        game.setDuration((int) ChronoUnit.SECONDS.between(game.getStartTime(), game.getEndTime()));     //Duration game
        gameService.save(game);
        return REDIRECT_TO_BOARDS+ game.getCode()+"/endGame";
    }

    @Transactional
    public String toLobby(Game game, ModelMap modelMap, Integer numberOfPlayers){

        modelMap.addAttribute(MESSAGE, "The game cannot start, there is only one player in the room");
        modelMap.addAttribute("game", game);
        
        int playerId = securityService.getCurrentPlayerId(); // Id of player that is logged

        Optional<Player> playerOp = playerService.findPlayerById(playerId);
        if(playerOp.isPresent()){
            Player pay = playerOp.get();
            modelMap.addAttribute(PLAYER, pay);
        }

        modelMap.addAttribute("totalplayers", numberOfPlayers);
        return "games/lobby";

    }

    @Transactional
    public void addDataToModel(Game game, ModelMap modelMap, Authentication authentication){

        User currentUser = (User) authentication.getPrincipal();       

        //Depending on whether the person is an administrator or a player, we add different things to the model

        if (securityService.isAdmin()) {

            int adminId = adminService.getIdAdminByName(currentUser.getUsername());
            Optional<Admin> adminOp = adminService.findAdminById(adminId);
            if(adminOp.isPresent()){
                modelMap.addAttribute(PLAYER, adminOp.get());
            }
        }else{
            int playerId = playerService.getIdPlayerByName(currentUser.getUsername());  
            Optional<Player> playerOp = playerService.findPlayerById(playerId);
            if(playerOp.isPresent()){
                modelMap.addAttribute(PLAYER, playerOp.get()); 
            }
        }
        modelMap.addAttribute("game", gameService.findGamesByRoomCode(game.getCode()).iterator().next());   
        modelMap.addAttribute("islands", gameService.findGamesByRoomCode(game.getCode()).iterator().next().getBoard().getIslands());

    }

    @Transactional
    public void addPlayerAtStartToSession(Game game, HttpServletRequest request){

        //This serves so that all the players have the information of the 
        //players who started the game, and can calculate the points correctly

        if(ChronoUnit.SECONDS.between(game.getTurnTime(), LocalDateTime.now())<=5){ 
            List<Player> players = game.getPlayers();
            List<Integer> playersAtStart = new ArrayList<>();
            players.forEach(p -> {
                p.setInGame(true);
                playerService.save(p);
                playersAtStart.add(p.getId());
            });    
            request.getSession().setAttribute(PLAYER_AT_START, playersAtStart);
        }

    }

    @Transactional
    public String changeTurn(Game g){

        String code = g.getCode();
        Integer n = g.getPlayers().size();

        //The current player and turn time changes
        g.setActualPlayer((g.getActualPlayer()+1)%n);   
        g.setTurnTime(LocalDateTime.now());
        g.setDieThrows(false);
        gameService.save(g);

        return REDIRECT_TO_BOARDS+ code;
    }

    @Transactional
    public String rollDie(Game game){

        Die d = new Die();
        int res = d.roll();

        game.setDieThrows(true);
        game.setValueOfDie(res);
        gameService.save(game);      

        return REDIRECT_TO_BOARDS+game.getCode()+"/actions/"+ res;
    }

    @Transactional
    public String doAnIllegalAction(String code, Integer island, Integer cardsToSpend, HttpServletRequest request){

        request.getSession().setAttribute(MESSAGE, "To travel to island "+island+ " you must use "+cardsToSpend +" cards");
        return REDIRECT_TO_BOARDS+ code;
        
    }

    @Transactional
    public String doCorrectAction(Game game, Integer island, Integer[] pickedCards, HttpServletRequest request){

        Player actualPlayer = new Player();
        List<Card> actualCards = new ArrayList<>();

        Optional<Player> playerOp=playerService.findPlayerById(game.getPlayers().get(game.getActualPlayer()).getId());
        if(playerOp.isPresent()){
            actualPlayer = playerOp.get();
            actualCards = actualPlayer.getCards();
        }
       
        if(pickedCards!=null){                      //If we give cards to move
            for(int i=0; i<pickedCards.length;i++){
                int n = pickedCards[i];
                Optional<Card> cardOp=actualCards.stream().filter(x->x.getId()==n).findFirst();
                if(cardOp.isPresent()){
                    actualCards.remove(cardOp.get());   //We lose those cards
                }
            }
        }       

        if(island<7){                               //If we choose an island and not the deck
            Deck d = game.getDeck();
            Card islandCard = game.getBoard().getIslands().get(island-1).getCard();

            if(islandCard!=null){                       //If the island has a card
                actualCards.add(islandCard);
                islandStatistics(actualPlayer ,islandCard, pickedCards, island);    

            }else{
                request.getSession().setAttribute(MESSAGE, "Island "+island+ " hasn't a card, choose another island");
                return REDIRECT_TO_BOARDS+ game.getCode();
            }
            chooseIsland(d, game, island);           
            
        }else{                                     //If we choose the deck
            actualCards = chooseDeck(game, actualCards);
        }

        actualPlayer.setCards(actualCards);
        playerService.save(actualPlayer);
        return REDIRECT_TO_BOARDS+game.getId()+"/changeTurn";
    }

    @Transactional
    public void islandStatistics(Player actualPlayer, Card islandCard, Integer[] pickedCards, Integer island){
        
        int statisticId = 0;
        Optional<Statistic> statisticOp=statisticService.getStatisticByPlayerId(actualPlayer.getId()).stream().filter(x->x.getHadWon()==null).findFirst();
        if(statisticOp.isPresent()){
            statisticId = statisticOp.get().getId();
        }
        
        if(pickedCards!=null){
            for(int i=0; i<pickedCards.length;i++){
                int n = pickedCards[i];
                if(statisticService.existsRow(statisticId, n)){
                    statisticService.updateCardCount(statisticId, n);
                }else{
                    statisticService.insertCardCount(statisticId, n);
                }
                    
            }
        }  
        
        statisticService.insertCardCount(statisticId, islandCard.getId());
        statisticService.updateIslandCount(statisticId, island);
        
    }

    @Transactional
    public void chooseIsland(Deck d, Game game, Integer island){
        
        if(!d.getCards().isEmpty()){        //The deck has cards
            Card c = null;

            Optional<Card> cardOp = d.getCards().stream().findFirst();
            if(cardOp.isPresent()){
                c = cardOp.get();
            }
            Island is = game.getBoard().getIslands().get(island-1);
            is.setCard(c);                  //we put a card on the chosen island
            d.deleteCards(c);
            deckService.save(d);
            islandService.save(is);

        }else{
            Island is = game.getBoard().getIslands().get(island-1);
            is.setCard(null);
            islandService.save(is);
        }         
        
    }

    @Transactional
    public List<Card> chooseDeck(Game game, List<Card> actualCards){
        
        Deck d = game.getDeck();
        Card c = null;
        Optional<Card> cardOp = d.getCards().stream().findFirst();
        if(cardOp.isPresent()){
            c = cardOp.get();
        }
        actualCards.add(c);         //We take the first card from the deck
        d.deleteCards(c);
        deckService.save(d);

        return actualCards;
        
    }

    @Transactional
    public String calculatePosibilities(Game game, HttpServletRequest request, int number){

        int cardsCurrently = game.getPlayers().get(game.getActualPlayer()).getCards().size();    //Cards that we have
        
        int limitSup = number + cardsCurrently;
        int limitInf = number - cardsCurrently;
        if(limitSup>7){             //LimitSup cannot be greater than 7 because there are 6 islands and a deck
            limitSup = 7;
        }
        if(limitInf<=0){            //LimitInf cannot be less than 1 because the first island occupies position 1
            limitInf = 1;
        }
        List<Integer> posib = IntStream.rangeClosed(limitInf, limitSup)
            .boxed().collect(Collectors.toList());
        
        List<Integer> posibilities = new ArrayList<>();
        for(Integer i: posib){
            if(i==7){           //If we choose to take a card from the deck
                List<Card> a = game.getDeck().getCards();
                if(!a.isEmpty()){
                    posibilities.add(i);
                }
            }else if(i >=1 && i <= 6 && game.getBoard().getIslands().get(i-1).getCard()!=null){ //If we choose to take a card from an island
               
                posibilities.add(i);
               
            }
        
        }    

        request.getSession().setAttribute(OPTIONS, posibilities);
        return REDIRECT_TO_BOARDS+ game.getCode();
    }

    @Transactional
    public String leaveGame(Player player, String code){

        player.setInGame(false);
        playerService.save(player);

        Game game = gameService.findGamesByRoomCode(code).iterator().next();
        List<Player> players = game.getPlayers();
        List<Player> newPlayers = players.stream().filter(x-> x!=player).collect(Collectors.toList());     //Remove the player of game player
        game.setPlayers(newPlayers);
        gameService.save(game);
        
        return "redirect:/welcome";
    }

    @Transactional
    public String endGame(Game game, ModelMap modelMap, HttpServletRequest request){
        
        List<Player> players = game.getPlayers();
        List<Integer> playersIdAtStart = (List<Integer>) request.getSession().getAttribute(PLAYER_AT_START);
        List<Player> playersAtStart = playersIdAtStart.stream().map(id -> playerService.findPlayerById(id).get()).collect(Collectors.toList());
    
        if(game.getEndTime()==null) return "/error";
        
        //Calculate the score of each player
        LinkedHashMap<Player, Integer> playersByPunctuation = calcPlayersByPunctuation(playersAtStart, players);

        //Set staisttics
        statisticService.setFinalStatistics(playersAtStart, playersByPunctuation, game);

        modelMap.put("playersByPunctuation", playersByPunctuation);

        return "games/endGame";
    }

    
    public Map<Integer, Integer> getPointsPerSet(){
        
        Map<Integer, Integer> values = new HashMap<>();

        //We declare the points per set

        values.put(1, 1);
        values.put(2, 3);
        values.put(3, 7);
        values.put(4, 13);
        values.put(5, 21);
        values.put(6, 30);
        values.put(7, 40);
        values.put(8, 50);
        values.put(9, 60); 
    
        return values;
    }

    @Transactional
    public Integer calcPoints(Integer numOfPoints, List<String> cards) {

        //Determine the points depending on the sets
        
        Map<Integer, Integer> pointsPerSet = this.getPointsPerSet();

        List<Set<String>> listOfSets = new ArrayList<>();
        while(!cards.isEmpty()){
            Set<String> notDuplicatedCard = new HashSet<>(cards);
            listOfSets.add(notDuplicatedCard);
            cards.removeAll(notDuplicatedCard);
        }
        
        for(Set<String> setOfCards : listOfSets){
            Integer sizeOfSet = setOfCards.size();
            numOfPoints += pointsPerSet.get(sizeOfSet);
        }

        return numOfPoints;
    }

    @Transactional
    public Map<Player, Pair> calcValuesPerPlayer(List<Player> players) {

        //We create a map to store the players with their points and their doubloons

        Map<Player, Pair> valuesPerPlayer = new HashMap<>();

        for(Player player : players){
            Integer numOfDoublons = (int) player.getCards().stream().filter(x-> x.getCardType().equals(CARD_TYPE.DOUBLON)).count();
            Integer numOfPoints = (int) player.getCards().stream().filter(x-> x.getCardType().equals(CARD_TYPE.DOUBLON)).count(); 
            List<String> cards = player.getCards().stream().filter(x->!x.getCardType().equals(CARD_TYPE.DOUBLON)).map(x->x.getCardType().toString()).collect(Collectors.toList());
            
            numOfPoints = this.calcPoints(numOfPoints, cards);

            Pair pointsDoublons = new Pair(numOfPoints,numOfDoublons);
    
            valuesPerPlayer.put(player, pointsDoublons);
            player.setInGame(false);
            playerService.save(player);
        }

        return valuesPerPlayer;
    }

    @Transactional
    public LinkedHashMap<Player, Integer> calcPlayersByPunctuation(List<Player> playersAtStart, List<Player> players) {
        Map<Player, Pair> valuesPerPlayers = this.calcValuesPerPlayer(players);
        
        LinkedHashMap<Player, Integer> playersByPunctuation = new LinkedHashMap<>();
        // Sort playersByPunctuation
        valuesPerPlayers.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue((e1,e2) -> e2.compareTo(e1)))
            .forEachOrdered(x -> playersByPunctuation.put(x.getKey(), x.getValue().x));

        // Set at 0 abbandoned players
        for(Player player : playersAtStart) {
            boolean playerHasAbbandoned = !playersByPunctuation.containsKey(player);
            if(playerHasAbbandoned) playersByPunctuation.put(player, 0);
        }

        return playersByPunctuation;
    }

}

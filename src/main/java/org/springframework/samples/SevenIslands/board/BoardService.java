package org.springframework.samples.SevenIslands.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.card.CARD_TYPE;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckRepository;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
import org.springframework.samples.SevenIslands.player.Player;
import org.springframework.samples.SevenIslands.player.PlayerService;
import org.springframework.samples.SevenIslands.statistic.Statistic;
import org.springframework.samples.SevenIslands.statistic.StatisticService;
import org.springframework.samples.SevenIslands.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private DeckRepository deckRepo;

    @Autowired
    private IslandService islandService;

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayerService playerService;
 
    @Autowired	
	private StatisticService statisticService;

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
    public void init(Game game) {  
        //Board board = new Board();
        List<Island> l = new ArrayList<>();
        for(int i=1;i<7;i++){
            Island isl = new Island();
            isl.setIslandNum(i);
            islandService.save(isl);
            l.add(isl);
        }
        //board.setIslands(l);
        Board board = new BoardBuilder().setIslands(l).build();
        game.setBoard(board);
        gameService.save(game);
        boardRepo.save(board);
        
    }

    @Transactional
    public void distribute(Board b, Deck d){
        
        if(d.getCards().size()!=0 && d.getCards().size()>=6){ //COMPROBAR ESTE IF QUE ES PARA CUANDO DECK NO TENGA CARTAS
            for(Island i: b.getIslands()){
                if(i.getCard()==null){  
                    List<Card> cards =  d.getCards();     
                    Card c = cards.stream().findFirst().get();  
                        if(c!=null){
                            d.getCards().remove(c);
                            i.setCard(c);   
                        }
                        
                }
            }
            
            deckRepo.save(d);
            boardRepo.save(b);

        }

    }

    @Transactional
    public void initCardPlayers(Game game){
        List<Player> players = game.getPlayers();
        Deck d = game.getDeck();
        for(Player p: players){
            List<Card> cards = new ArrayList<>();      
            List<Card> doblones = d.getCards().stream().filter(x->x.getCardType().equals(CARD_TYPE.DOUBLON)).limit(3).collect(Collectors.toList());
            d.getCards().removeAll(doblones);
            deckRepo.save(d);
            cards.addAll(doblones);
            p.setCards(cards);
            Statistic s = new Statistic();
            //s.setGame(game);
            s.setPlayer(p);

            
            
            statisticService.save(s);

            statisticService.insertinitIslandCount(s.getId(),1);
            statisticService.insertinitIslandCount(s.getId(),2);
            statisticService.insertinitIslandCount(s.getId(),3);
            statisticService.insertinitIslandCount(s.getId(),4);
            statisticService.insertinitIslandCount(s.getId(),5);
            statisticService.insertinitIslandCount(s.getId(),6);
            p.getStatistic().add(s);;
                  
            
            //p.setInGame(true);
            playerService.save(p);
        }
    }

    @Transactional
    public Map<Integer, Integer> getPointsPerSet(){
        
        Map<Integer, Integer> values = new HashMap<>();

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

    public Integer calcPoints(Integer numOfPoints, List<String> cards) {
        Map<Integer, Integer> pointsPerSet = this.getPointsPerSet();

        List<Set<String>> listOfSets = new ArrayList<>();
        while(cards.size()!=0){
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

    public Map<Player, Pair> calcValuesPerPlayer(List<Player> players) {
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
            Boolean playerHasAbbandoned = !playersByPunctuation.containsKey(player);
            if(playerHasAbbandoned) playersByPunctuation.put(player, 0);
        }

        return playersByPunctuation;
    }

}

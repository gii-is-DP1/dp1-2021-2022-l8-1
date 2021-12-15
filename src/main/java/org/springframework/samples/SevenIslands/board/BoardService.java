package org.springframework.samples.SevenIslands.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        Board board = new Board();
        List<Island> l = new ArrayList<>();
        for(int i=1;i<7;i++){
            Island isl = new Island();
            isl.setIslandNum(i);
            islandService.save(isl);
            l.add(isl);
        }
        board.setIslands(l);
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
            List<Card> cards = p.getCards();
            List<Card> doblones = d.getCards().stream().filter(x->x.getCardType().equals(CARD_TYPE.DOUBLON)).limit(3).collect(Collectors.toList());
            d.getCards().removeAll(doblones);
            deckRepo.save(d);
            cards.addAll(doblones);
            p.setCards(cards);
            playerService.save(p);
        }
    }

}

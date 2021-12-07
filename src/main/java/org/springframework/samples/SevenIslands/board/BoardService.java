package org.springframework.samples.SevenIslands.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.deck.Deck;
import org.springframework.samples.SevenIslands.deck.DeckRepository;
import org.springframework.samples.SevenIslands.game.Game;
import org.springframework.samples.SevenIslands.game.GameService;
import org.springframework.samples.SevenIslands.island.Island;
import org.springframework.samples.SevenIslands.island.IslandService;
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
        
        for(Island i: b.getIslands()){
            if(i.getCard()==null){   
                Card c = d.getCards().stream().findFirst().get();       
                d.getCards().remove(c);
                i.setCard(c);
                //PONER IF PARA CUANDO DECK NO TENGA CARTAS
            }
        }
        deckRepo.save(d);
        boardRepo.save(b);
    }

}

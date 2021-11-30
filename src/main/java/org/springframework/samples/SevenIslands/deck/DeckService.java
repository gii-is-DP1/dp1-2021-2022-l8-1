package org.springframework.samples.SevenIslands.deck;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepo;

    @Transactional
    public int getDecksNumber() {  
        return (int) deckRepo.count();
    }

    @Transactional
    public Iterable<Deck> findByCardId(int id) {
        return deckRepo.findByCardId(id);
    }

    @Transactional
    public Iterable<Integer> getCardsOnDeck(int deckId) {  
        return deckRepo.findCardsInDeck(deckId);
    }

    @Transactional
    public void save(Deck deck) {  
        deckRepo.save(deck);
    }

    @Transactional
    public Deck getDeck(int id) {  
        return deckRepo.findDeckById(id);
    }


}

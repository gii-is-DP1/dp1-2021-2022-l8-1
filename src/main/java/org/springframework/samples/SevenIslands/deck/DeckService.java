package org.springframework.samples.SevenIslands.deck;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.card.CardService;
import org.springframework.stereotype.Service;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepo;

    @Autowired
    private CardService cardService;


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

    @Transactional
    public Deck init(String name) {  
        Deck deck = new Deck();
        deck.setName(name);
        Iterable<Card> cardsI = cardService.findAll();
        List<Card> cards = StreamSupport.stream(cardsI.spliterator(), false).collect(Collectors.toList());
        deck.setCards(cards);
        deckRepo.save(deck);
        return deck;
    }


}

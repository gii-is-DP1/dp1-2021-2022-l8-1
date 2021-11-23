package org.springframework.samples.SevenIslands.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DeckServiceTests {

    @Autowired
    private DeckService deckService;

    @Test
    public void testCountWithInitialData() {
        int count = deckService.getDecksNumber();
        assertEquals(1, count);
    }

    @Test
    public void testFindByCardId(){
        Iterable<Deck> decks = deckService.findByCardId(1);
        assertEquals(decks.spliterator().getExactSizeIfKnown(), 1);
    }

    @Test
    public void testFindCardsOnDeck(){
        Iterable<Integer> cards = deckService.getCardsOnDeck(1);
        assertEquals(cards.spliterator().getExactSizeIfKnown(), 2);
    }

    
    
}

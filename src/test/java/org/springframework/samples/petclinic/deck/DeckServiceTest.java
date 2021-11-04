package org.springframework.samples.petclinic.deck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class DeckServiceTest {

    @Autowired
    private DeckService deckService;

    @Test
    public void testCountWithInitialData() {
        int count = deckService.getDeckSize();
        assertEquals(2, count);
    }
    
}

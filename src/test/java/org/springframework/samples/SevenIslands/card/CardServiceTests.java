package org.springframework.samples.SevenIslands.card;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CardServiceTests {

    @Autowired
    private CardService cardService;
    
    @Test
    public void testCountWithInitialData(){
        int count = cardService.cardCount();
        assertEquals(66,count);
    }

    @Test
    public void testFindAll(){
        long count = cardService.findAll().spliterator().getExactSizeIfKnown();
        assertEquals(66, count);
    }

    @Test
    public void testFindCardById(){
        Card card = cardService.findCardById(1).get();
        assertThat(card.getCardType()).isEqualTo(CARD_TYPE.DOUBLON);
    }
    
}

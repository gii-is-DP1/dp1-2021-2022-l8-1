package org.springframework.samples.SevenIslands.inappropiateWord;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWord;
import org.springframework.samples.SevenIslands.inappropriateWord.InappropiateWordService;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class InappropiateWordServiceTest {
    @Autowired
    private InappropiateWordService inappropiateWordService;

    @Test
    public void testCountWithInitialData() {
        int count = inappropiateWordService.findInappropiateWordsNumber();
        assertEquals(11, count);
    }

    @Test
    public void testCounyAll() {
        Iterable<InappropiateWord> words = inappropiateWordService.findAll();
        List<InappropiateWord> listWords = StreamSupport.stream(words.spliterator(), false).collect(Collectors.toList());
        assertEquals(11, listWords.size());
    }
    
}

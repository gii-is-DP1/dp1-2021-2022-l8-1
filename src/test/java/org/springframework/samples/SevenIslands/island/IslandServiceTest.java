package org.springframework.samples.SevenIslands.island;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class IslandServiceTest {

    @Autowired
    private IslandService islandService;
    
    @Test
    public void testCountWithInitialData(){
        int count = islandService.islandCount();
        assertEquals(6, count);
    }

    @Test
    public void testfindCardOnIsland(){  
        Integer card = islandService.islandCard(6);
        assertTrue("Pass", card!=null);   
        assertFalse("Not Pass", card==null);
    }
   
}

package org.springframework.samples.SevenIslands.cell;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class CellServiceTests {
    
    @Autowired
    private CellService cellService;
    
    @Test
    public void testCountWithInitialData(){
        int count = cellService.cellCount();
        assertEquals(count,1);
    }
}
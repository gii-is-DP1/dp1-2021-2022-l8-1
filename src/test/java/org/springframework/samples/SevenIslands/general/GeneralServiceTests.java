package org.springframework.samples.SevenIslands.general;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GeneralServiceTests {
    
    @Autowired
    private GeneralService generalService;
    
    @Test
    public void testCountWithInitialData(){
        int count = generalService.generalCount();
        assertEquals(count,1);
    }
}

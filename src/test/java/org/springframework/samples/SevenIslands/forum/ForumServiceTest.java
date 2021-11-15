package org.springframework.samples.SevenIslands.forum;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class ForumServiceTest {
    @Autowired
    private ForumService forumService;

    @Test
    public void testCountWithInitialData(){
        int count = forumService.forumCount();
        assertEquals(count, 2);
    }

    @Test
    public void testFindByTopicId(){
        Iterable<Forum> forums = forumService.findByTopicId(1);
        assertEquals(forums.spliterator().getExactSizeIfKnown(), 1);
    }
}

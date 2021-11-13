package org.springframework.samples.petclinic.topic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class TopicServiceTest {
    @Autowired
    private TopicService topicService;

    @Test
    public void testCountWithInitialData(){
        int count = topicService.topicCount();
        assertEquals(count, 1);
    }

    @Test
    public void testFindByCommentId(){
        Iterable<Topic> topics = topicService.findByCommentId(1);
        assertEquals(topics.spliterator().getExactSizeIfKnown(), 1);
    }
    
}

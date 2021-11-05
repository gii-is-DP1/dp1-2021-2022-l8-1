package org.springframework.samples.petclinic.topic;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepo;

    @Transactional
    public int topicCount(){
        return (int) topicRepo.count();
    }
    
}

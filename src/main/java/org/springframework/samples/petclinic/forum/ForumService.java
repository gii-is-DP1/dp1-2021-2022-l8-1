package org.springframework.samples.petclinic.forum;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForumService {
    @Autowired
    private ForumRepository forumRepo;

    @Transactional
    public int forumCount(){
        return (int) forumRepo.count();
    }
    
}

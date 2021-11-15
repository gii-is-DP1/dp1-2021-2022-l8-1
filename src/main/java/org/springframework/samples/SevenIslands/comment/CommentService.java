package org.springframework.samples.SevenIslands.comment;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Transactional
    public int commentCount(){
        return (int) commentRepo.count();
    }
    
}

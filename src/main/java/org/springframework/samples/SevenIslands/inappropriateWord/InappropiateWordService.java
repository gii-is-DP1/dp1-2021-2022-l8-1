package org.springframework.samples.SevenIslands.inappropriateWord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InappropiateWordService {
    
    @Autowired
    private InappropiateWordRepository inappropiateWordRepo;

    @Transactional
    public int findInappropiateWordsNumber() {  
        return (int) inappropiateWordRepo.count();
    }

    @Transactional(readOnly = true)
    public Iterable<InappropiateWord> findAll(){
        return inappropiateWordRepo.findAll();
    }
    
}

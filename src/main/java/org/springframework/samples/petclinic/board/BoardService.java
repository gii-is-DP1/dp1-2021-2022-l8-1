package org.springframework.samples.petclinic.board;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepo;

    @Transactional
    public int boardCount(){
        return (int) boardRepo.count();
    }
    
}

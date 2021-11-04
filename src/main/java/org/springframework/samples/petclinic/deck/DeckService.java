package org.springframework.samples.petclinic.deck;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepo;

    @Transactional
    public int getDecksNumber() {  
        return (int) deckRepo.count();
    }
}

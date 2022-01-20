package org.springframework.samples.SevenIslands.card;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    
    @Autowired
    private CardRepository cardRepo;
    
    @Transactional
    public int cardCount() {
        return (int) cardRepo.count();
    }

    @Transactional
    public Iterable<Card> findAll() {
        return cardRepo.findAll();
    }

    @Transactional
    public Optional<Card> findCardById(int id) {
        return cardRepo.findById(id);
    }

}

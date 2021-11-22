package org.springframework.samples.SevenIslands.person;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepo;

    @Transactional
    public int personCount(){
        return (int) personRepo.count();
    }

    @Transactional
    public Iterable<Person> findAll(){
        return personRepo.findAll();
    }

    @Transactional
    public Optional<Person> findPersonById(int id){
        return personRepo.findById(id);
    }
}

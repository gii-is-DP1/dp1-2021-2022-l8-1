package org.springframework.samples.petclinic.general;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeneralService {
    @Autowired
    private GeneralRepository generalRepo;

    @Transactional
    public int generalCount(){
        return (int) generalRepo.count();
    }


}

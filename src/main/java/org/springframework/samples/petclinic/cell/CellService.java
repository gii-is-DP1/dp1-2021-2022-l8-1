package org.springframework.samples.petclinic.cell;

import org.springframework.transaction.annotation.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellService {
    @Autowired
    private CellRepository cellRepo;

    @Transactional
    public int cellCount(){
        return (int) cellRepo.count();
    }

}


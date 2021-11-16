package org.springframework.samples.SevenIslands.admin;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepo;

    @Transactional
    public int getNumberOfAdmins() {
        return (int) adminRepo.count();
    }
    
}

package org.springframework.samples.SevenIslands.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository adminRepo;

    @Transactional
    public int getNumberOfAdmins() {
        return (int) adminRepo.count();
    }

    @Transactional(readOnly = true)
    public Integer getIdAdminByName(String n) {
        return adminRepo.findAdminIdByName(n);
    }
    @Transactional(readOnly = true)
    public Optional<Admin> getAdminByName(String n) {
        return adminRepo.findAdminByName(n);
    }
    @Transactional
    public Optional<Admin> findAdminById(int id){
        return adminRepo.findById(id);
    }
    @Transactional
    public void save(Admin admin){
        adminRepo.save(admin);
    }
}

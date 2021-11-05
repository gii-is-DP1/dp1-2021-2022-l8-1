package org.springframework.samples.petclinic.admin;

import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    
    
}

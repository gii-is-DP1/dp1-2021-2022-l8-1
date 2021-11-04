package org.springframework.samples.petclinic.forum;

import org.springframework.data.repository.CrudRepository;

public interface ForumRepository extends CrudRepository<Forum, Integer>{
    
}

package org.springframework.samples.petclinic.comment;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer>{
    
}

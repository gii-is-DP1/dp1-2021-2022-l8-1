package org.springframework.samples.SevenIslands.comment;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer>{
    
}

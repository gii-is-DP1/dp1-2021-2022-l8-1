package org.springframework.samples.petclinic.topic;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TopicRepository extends CrudRepository<Topic, Integer>{
    @Query("SELECT T FROM Topic T INNER JOIN T.comments C WHERE C.id = :commentId")
	Collection<Topic> findByCommentId(@Param("commentId") int commentId) throws DataAccessException;
    
}

package org.springframework.samples.SevenIslands.forum;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ForumRepository extends CrudRepository<Forum, Integer>{
    @Query("SELECT F FROM Forum F INNER JOIN F.topics T WHERE T.id = :topicId")
	Collection<Forum> findByTopicId(@Param("topicId") int topicId) throws DataAccessException;
}

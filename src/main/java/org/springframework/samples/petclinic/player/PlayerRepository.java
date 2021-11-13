package org.springframework.samples.petclinic.player;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Collection;
import org.springframework.data.repository.query.Param;
import org.springframework.dao.DataAccessException;

public interface PlayerRepository extends CrudRepository<Player, Integer>{
    @Query("SELECT P FROM Player P INNER JOIN P.forums F WHERE F.id = :forumId")
	Collection<Player> findByForumId(@Param("forumId") int forumId) throws DataAccessException;
}
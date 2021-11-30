package org.springframework.samples.SevenIslands.card;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CardRepository extends CrudRepository<Card, Integer>{

    // @Query("SELECT C FROM Card C INNER JOIN C.players P WHERE P.id = :playerId")
    // Collection<Card> getByPlayerId(@Param("playerId") int playerId) throws DataAccessException;
}

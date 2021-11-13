package org.springframework.samples.petclinic.deck;

import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeckRepository extends CrudRepository<Deck, Integer> {
    //Puede servir para saber si una carta está en deck: 0 si no está, 1 si está
    @Query("SELECT D FROM Deck D INNER JOIN D.cards C WHERE C.id = :cardId")
	Collection<Deck> findByCardId(@Param("cardId") int cardId) throws DataAccessException;
}




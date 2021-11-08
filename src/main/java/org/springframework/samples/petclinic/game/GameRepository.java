package org.springframework.samples.petclinic.game;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface GameRepository extends CrudRepository<Game, Integer>{

@Query("SELECT g.numberOfPlayers FROM Game g WHERE g.id = :id")
int findTotalPlayers(int id) throws DataAccessException;

}

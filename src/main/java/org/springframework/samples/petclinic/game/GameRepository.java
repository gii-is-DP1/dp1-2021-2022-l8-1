package org.springframework.samples.petclinic.game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer>{}

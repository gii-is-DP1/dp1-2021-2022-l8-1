package org.springframework.samples.SevenIslands.island;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface IslandRepository extends CrudRepository<Island, Integer>{

    @Query(value = "SELECT I.card_id FROM islands I where I.id = ?1", nativeQuery = true)
    Integer findCardOnIsland(Integer id);


}

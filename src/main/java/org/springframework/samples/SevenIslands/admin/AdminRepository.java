package org.springframework.samples.SevenIslands.admin;

import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
    
// @Query(value = "SELECT * FROM GAMES WHERE PLAYER_ID LIKE ?1", nativeQuery = true)
// Collection<Admin> findAdminsById(@Param("playerId") int playerId) throws DataAccessException
    
}

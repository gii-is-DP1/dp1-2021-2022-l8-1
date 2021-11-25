package org.springframework.samples.SevenIslands.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String>{

    @Modifying
        @Query(value = "DELETE FROM USERS WHERE USERNAME LIKE ?1", nativeQuery = true)
        public void deleteUser(String username);
        
	
}

package org.springframework.samples.SevenIslands.user;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface AuthoritiesRepository extends CrudRepository<Authorities, String>{

    @Modifying
        @Query(value = "DELETE FROM AUTHORITIES WHERE ID LIKE ?1", nativeQuery = true)
        public void deleteById(int id);
	
}

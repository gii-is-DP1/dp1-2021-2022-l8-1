package org.springframework.samples.SevenIslands.admin;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Integer> {

    @Query(value = "SELECT A.id FROM Admins A JOIN Users U ON U.username=A.username WHERE A.username LIKE ?1", nativeQuery = true)
	Integer findAdminIdByName(String n);

    @Query(value = "SELECT A.* FROM Admins A JOIN Users U ON U.username=A.username WHERE A.username LIKE ?1", nativeQuery = true)
	Optional<Admin> findAdminByName(String n);
    
}

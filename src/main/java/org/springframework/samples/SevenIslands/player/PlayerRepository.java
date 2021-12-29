package org.springframework.samples.SevenIslands.player;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;


public interface PlayerRepository extends PagingAndSortingRepository<Player, Integer>{

	@Query("SELECT P FROM Player P INNER JOIN P.forums F WHERE F.id = :forumId")
	Collection<Player> findByForumId(@Param("forumId") int forumId) throws DataAccessException;

	@Query("SELECT A FROM Player A INNER JOIN A.games P WHERE P.id = :gameId")
	Collection<Player> findByGameId(@Param("gameId") int gameId) throws DataAccessException;

	@Query("SELECT P FROM Player P WHERE P.id = :playerId")
	Collection<Player> findWatchGameByPlayerId(@Param("playerId") int playerId) throws DataAccessException;

	@Query("SELECT P FROM Player P WHERE P.surname LIKE :surname%")
	Collection<Player> findBySurname(@Param("surname") String surname);
	
	@Query(value = "SELECT * FROM Players WHERE last_modified_date IS NOT null ORDER BY last_modified_date DESC", nativeQuery = true)	
	Collection<Player> findAuditPlayers();

	@Query(value = "SELECT P.id FROM Players P JOIN Users U ON U.username=P.username WHERE P.username LIKE ?1", nativeQuery = true)	
	Integer findPlayerIdByName(String n);

	@Query(value = "SELECT * FROM Players P JOIN Users U ON U.username=P.username WHERE P.username LIKE ?1", nativeQuery = true) 
	Collection<Player> findPlayerByUsername(String n);

	@Query(value = "SELECT * FROM Players where id LIKE ?1", nativeQuery = true) 
	Optional<Player> findPlayerById(int id);

	//@Query("SELECT P FROM Player P INNER JOIN P.user U WHERE ( P.surname LIKE %:data% or U.username LIKE %:data% or P.firstName LIKE %:data%)")
	//Iterable<Player> findIfPlayerContains(@Param("data") String data, @Param("pageable") Pageable pageable)

	@Query("SELECT P FROM Player P INNER JOIN P.user U WHERE ( LOWER(P.surname) LIKE %:data% or LOWER(U.username) LIKE %:data% or LOWER(P.firstName) LIKE %:data%)")
	Page<Player> findIfPlayerContains(@Param("data") String data, Pageable pageable);

}
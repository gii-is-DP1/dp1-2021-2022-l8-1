package org.springframework.samples.petclinic.board;

import org.springframework.data.repository.CrudRepository;

public interface BoardRepository extends CrudRepository<Board, Integer>{}

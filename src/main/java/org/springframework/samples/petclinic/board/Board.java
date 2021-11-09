package org.springframework.samples.petclinic.board;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="boards")
public class Board extends BaseEntity{
    
}

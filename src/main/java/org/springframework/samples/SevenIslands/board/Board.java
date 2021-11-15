package org.springframework.samples.SevenIslands.board;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.SevenIslands.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="boards")
public class Board extends BaseEntity{
    
}

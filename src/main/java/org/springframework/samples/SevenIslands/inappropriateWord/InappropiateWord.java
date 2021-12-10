package org.springframework.samples.SevenIslands.inappropriateWord;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.samples.SevenIslands.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "inappropiateWords")
public class InappropiateWord extends BaseEntity{

    @Column(name="name")
    private String name;
    
}

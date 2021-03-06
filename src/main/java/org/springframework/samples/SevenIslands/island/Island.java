package org.springframework.samples.SevenIslands.island;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.samples.SevenIslands.card.Card;
import org.springframework.samples.SevenIslands.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="islands")
public class Island extends BaseEntity {
    
    @Column(name="island_num")
    @Min(value = 1)
    @Max(value = 7)
    private Integer islandNum;

    @OneToOne(optional=true)
    private Card card;
    
}
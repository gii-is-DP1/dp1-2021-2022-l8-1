package org.springframework.samples.petclinic.island;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.BaseEntity;

import lombok.Data;

@Data
@Entity
@Table(name="islands")
public class Island extends BaseEntity {
    
    @Column(name="island_num")
    @NotEmpty
    @Min(value = 1)
    @Max(value = 7)
    private Integer islandNum;
    
}
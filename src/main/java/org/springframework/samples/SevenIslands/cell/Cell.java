package org.springframework.samples.SevenIslands.cell;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.SevenIslands.card.CARD_TYPE;
import org.springframework.samples.SevenIslands.model.BaseEntity;
import org.springframework.samples.SevenIslands.model.NamedEntity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name="cells")
@Entity
public class Cell extends BaseEntity {
    @Column(name="position")
    private Integer position;
    
    @Enumerated(EnumType.STRING)
    @Column(name="card")
    @NotEmpty
    private CARD_TYPE card;  
}

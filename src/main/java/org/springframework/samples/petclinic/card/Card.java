package org.springframework.samples.petclinic.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "cards")
public class Card extends NamedEntity {

    @Column(name = "card_type")
    @NotEmpty
    private CARD_TYPE cardType;
}

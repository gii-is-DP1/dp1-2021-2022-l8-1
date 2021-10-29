package org.springframework.samples.petclinic.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "cards")
public class Card {

    @Column(name = "card_type")
    @NotEmpty
    private CARD_TYPE cardType;
}

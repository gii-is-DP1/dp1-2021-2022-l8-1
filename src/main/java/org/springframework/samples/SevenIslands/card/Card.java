package org.springframework.samples.SevenIslands.card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.SevenIslands.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card extends BaseEntity {

    @Column(name = "card_type")
    @Enumerated(EnumType.STRING)
    @NotEmpty
    private CARD_TYPE cardType;
    
    @Column(name = "image_url")
    //@NotEmpty
    private String imageUrl; // TODO: needs to be added in data.sql
    
}

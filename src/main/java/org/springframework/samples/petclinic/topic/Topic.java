package org.springframework.samples.petclinic.topic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="topics")
public class Topic extends NamedEntity{
    @NotEmpty
    @Column(name="description")
    private String description;
}

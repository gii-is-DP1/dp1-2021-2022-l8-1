package org.springframework.samples.petclinic.forum;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.samples.petclinic.model.NamedEntity;

import lombok.Data;

@Data
@Entity
@Table(name="forums")
public class Forum extends NamedEntity{
    @NotEmpty
    private String description;
    
}

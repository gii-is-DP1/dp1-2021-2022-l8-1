package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;

@Data
@Entity
public class Payment extends AuditableEntity{
	double amount;
	@OneToOne
	Owner owner;
}

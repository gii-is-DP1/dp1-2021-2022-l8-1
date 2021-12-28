package org.springframework.samples.SevenIslands.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.samples.SevenIslands.web.jsonview.Views;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{

	@JsonView(Views.Public.class)
	@NotEmpty
	@Id
	String username;
	
	@PasswordConstraint
	String password;
	
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private Set<Authorities> authorities;
}

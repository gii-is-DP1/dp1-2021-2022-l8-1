package org.springframework.samples.SevenIslands.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User{
	@NotEmpty
	@Id
	String username;
	
	@NotEmpty
	// @Pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[ª@~$€!¡¿?|#º/\\<>{}+-])[A-Za-z\d@$!%*?&]{9,}$")
	// @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[ª@~$€!¡¿?|#º/\\<>{}+-])[A-Za-z\\d@$!%*?&]{9,}$",message="Must contain at least one number and one uppercase and lowercase letter, and at least 9 or more characters")  
	String password;
	
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Authorities> authorities;
}

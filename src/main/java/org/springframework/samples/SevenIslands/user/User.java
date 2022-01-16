package org.springframework.samples.SevenIslands.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.samples.SevenIslands.web.jsonview.Views;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Audited
@Table(name = "users")
public class User{

	public User(String username2, String password2, Boolean enabled2) {
		this.username=username2;
		this.password=password2;
		this.enabled=enabled2;

	}

	public User() {

	}

	@JsonView(Views.Public.class)
	@NotEmpty
	@Id
	String username;
	
	@PasswordConstraint
	String password;
	
	@NotAudited
	boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	@NotAudited
	private Set<Authorities> authorities;
}

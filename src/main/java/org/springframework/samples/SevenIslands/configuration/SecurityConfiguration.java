package org.springframework.samples.SevenIslands.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author japarejo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String ADMIN = "admin";
    private static final String PLAYER = "player";

	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/resources/**","/webjars/**","/h2-console/**").permitAll()
				.antMatchers(HttpMethod.GET, "/","/oups").permitAll()
				.antMatchers("/currentuser").permitAll()
				.antMatchers("/users/new").permitAll()
				.antMatchers("/welcome").permitAll()
				.antMatchers("/games").permitAll()
				.antMatchers("/boards/**").hasAnyAuthority(PLAYER,ADMIN)
				.antMatchers("/error/**").permitAll()
        		.antMatchers("/achievements/**").hasAnyAuthority(ADMIN)
				.antMatchers("/players").permitAll()
				.antMatchers("/players/auditing").hasAnyAuthority(ADMIN)
				.antMatchers("/players/**").permitAll()
				.antMatchers("/games/**").permitAll()
				.antMatchers("/admins/**").hasAnyAuthority(ADMIN)
				.antMatchers("/admin/**").hasAnyAuthority(ADMIN)
				.antMatchers("/ranking").hasAnyAuthority(PLAYER, ADMIN)
				.antMatchers("/ranking/**").hasAnyAuthority(PLAYER, ADMIN)
				.anyRequest().denyAll()
				.and()
				 	.formLogin()
				 	.loginPage("/login").permitAll()
				.and()
					.logout().permitAll()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout")); 


        // Configuration for the administration console of the DB H2 to work (disable the protection headers against
        // csrf type attacks and enable framesets if your content is served from this same page.
		http.csrf().ignoringAntMatchers("/h2-console/**")
					.and()
					.exceptionHandling().accessDeniedHandler(new AccessDeniedExceptionHandler());
		http.headers().frameOptions().sameOrigin();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
	      .dataSource(dataSource)
	      .usersByUsernameQuery(
	       "select username,password,enabled "
	        + "from users "
	        + "where username = ?")
	      .authoritiesByUsernameQuery(
	       "select username, authority "
	        + "from authorities "
	        + "where username = ?")	      	      
	      .passwordEncoder(passwordEncoder());	
	}

	
	@Bean
	public PasswordEncoder passwordEncoder() {	    
		PasswordEncoder encoder =  NoOpPasswordEncoder.getInstance();
	    return encoder;
	}
	
}



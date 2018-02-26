package com.uniovi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * Pregunta de teoria Para seguridad definir WebSecurityConfig extends
	 * WebSecurityConfigurerAdapter Implementar SecurityService y
	 * UserDetailsServiceImpl implements UserDetailsService
	 */
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configura a que url x usuarios tiene permiso para acceder
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests() // peticiones autorizadas
				// Permite a todos los usuarios
				.antMatchers("/css/**", "/img/**", "/script/**", "/", "/signup",
						"/login/**").permitAll()
				// Especifica usuario que modificar
				.antMatchers("/mark/add").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/mark/add").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated().and().
				// pagina de autentificacion por defecto
				formLogin().loginPage("/login").permitAll()
				// Si se loguea bien
				.defaultSuccessUrl("/home").and().logout().permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(bCryptPasswordEncoder());
	}
}
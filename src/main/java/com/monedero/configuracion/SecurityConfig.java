package com.monedero.configuracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.monedero.servicios.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(authorizeRequests -> 
				authorizeRequests
					.requestMatchers("/login", "/h2-console/**", "/register/**", "/assets/**", "/css/**", "/js/**", "/images/**").permitAll() // permitir acceso a todas estas rutas
					.anyRequest().authenticated()
			)
			.formLogin(formLogin ->
				formLogin
					.loginPage("/login") // nuestra página personalizada
					.defaultSuccessUrl("/", true)
					.permitAll()
			)
			.logout(logout ->
				logout
					.logoutUrl("/logout") // URL para logout
					.logoutSuccessUrl("/login?logout") // Redirección después de logout
					.permitAll()
			)
			.headers(headers -> headers.frameOptions().sameOrigin()) // permitir que h2 se muestre en un iframe
			.userDetailsService(userDetailsService);
		
		return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

package com.example.authapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	private final AuthenticationProvider authenticationProvider;
	private final JwtAuthFilter jwtAuthFilter;

	public SecurityConfig(final AuthenticationProvider authenticationProvider, final JwtAuthFilter jwtAuthFilter) {
		this.authenticationProvider = authenticationProvider;
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(
						auth -> auth
								.antMatchers("/login", "/sign-up", "/v2/api-docs", "/swagger-resources/**",
										"/swagger-ui.html", "/swagger-ui/**", "/webjars/**")
								.permitAll().anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}

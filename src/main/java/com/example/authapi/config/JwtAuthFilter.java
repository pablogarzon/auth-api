package com.example.authapi.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.authapi.dtos.ErrorDTO;
import com.example.authapi.services.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JWTService jwtService;
	private final UserDetailsService userDetailsService;
	private final ObjectMapper objectMapper;

	public JwtAuthFilter(final JWTService jwtService, final UserDetailsService userDetailsService, final ObjectMapper objectMapper) {
		this.jwtService = jwtService;
		this.userDetailsService = userDetailsService;
		this.objectMapper = objectMapper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			final String token = authHeader.substring(7); // quitar "Bearer "
			final String email = jwtService.getSubject(token);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

			if (email != null && authentication == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
				if (jwtService.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
				}
			}
			filterChain.doFilter(request, response);
		} catch (UsernameNotFoundException | JwtException ex) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");

			ErrorDTO error = new ErrorDTO(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
			String body = objectMapper.writeValueAsString(error);
			
			response.getWriter().write(body);
		}
	}

}
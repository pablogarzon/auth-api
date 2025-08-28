package com.example.authapi.services.impl;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.authapi.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {
	private final SecretKey secretKey;

	public JWTService(@Value("${jwt.secret}") final String base64Secret) {
		this.secretKey = getKey(base64Secret);
	}

	public String generateToken(final User user) {
		var claims = new HashMap<String, Object>();
		return Jwts.builder().claims().add(claims).subject(user.getUsername()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 10)).and().signWith(secretKey).compact();
	}

	private SecretKey getKey(final String base64Secret) {
		byte[] decode = Decoders.BASE64.decode(base64Secret);
		return Keys.hmacShaKeyFor(decode);
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final var payload = extractPayload(token);
		final String username = payload.getSubject();
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
	
	public String getSubject(String token) {
		return extractPayload(token).getSubject();
	}
	
	
	private boolean isTokenExpired(String token) {
		return extractPayload(token).getExpiration().before(new Date());
    }

    private Claims extractPayload(String token) {
        return Jwts
                .parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
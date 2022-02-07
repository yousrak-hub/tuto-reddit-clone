package com.reddit.demo.security;


import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.security.core.userdetails.User;


@Service
public class JwtProvider {

	@Value("${jwt.key}")
	private String jwtSecret;
	@Value("${jwt.expiration.time}")
	private Long expirationInMillis;

	public String generateToken(Authentication authentication) {
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusSeconds(expirationInMillis)))
				  .signWith(SignatureAlgorithm.HS512, jwtSecret).compact(); 
	}

	public Claims validateToken(String jwt) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody();
		if (claims.getExpiration().before(Date.from(Instant.now()))) {
			throw new RuntimeException("Exired token!");
		}
		return claims;
	}

	public Long getJwtExpirationInMillis() {
		return expirationInMillis;
	}

	public String generateTokenWithUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(Instant.now().plusSeconds(expirationInMillis)))
				  .signWith(SignatureAlgorithm.HS512, jwtSecret).compact(); 
	}
}

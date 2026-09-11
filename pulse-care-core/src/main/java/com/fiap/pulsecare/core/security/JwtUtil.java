package com.fiap.pulsecare.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

	private final SecretKey secretKey;
	private final long expirationMillis;

	public JwtUtil(
			@Value("${pulsecare.security.jwt.secret}") String secret,
			@Value("${pulsecare.security.jwt.expiration-ms:3600000}") long expirationMillis) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
		this.expirationMillis = expirationMillis;
	}

	public String generateToken(String email, String role) {

		Date now = new Date();
		Date expiry = new Date(now.getTime() + expirationMillis);

		return Jwts.builder()
				.subject(email)
				.claim("role", role)
				.issuedAt(now)
				.expiration(expiry)
				.signWith(secretKey)
				.compact();
	}

	public Claims parseClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}

	public String extractEmail(String token) {
		return parseClaims(token).getSubject();
	}

	public String extractRole(String token) {
		return parseClaims(token).get("role", String.class);
	}

	public boolean isValid(String token) {
		try {
			return parseClaims(token).getExpiration().after(new Date());
		} catch (Exception e) {
			return false;
		}
	}

}

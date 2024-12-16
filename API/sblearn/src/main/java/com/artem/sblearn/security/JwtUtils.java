/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.artem.sblearn.security;

import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import javax.crypto.SecretKey;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

/**
 *
 * @author abdul.haseeb
 */
@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${JWT_SECRET}")
	private String jwtSecret;

	@Value("${JWT_EXPIRATION}")
	private int jwtExpirationMs;

	@Value("${JWT_COOKIE_NAME}")
	private String jwtCookie;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public ResponseCookie generateToken(String username, Set<String> roles) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", roles);
		Long dateInM = System.currentTimeMillis() + jwtExpirationMs;
		String jwt = createToken(claims, username, dateInM);
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(dateInM).httpOnly(true).build();
		return cookie;
	}

	private String createToken(Map<String, Object> claims, String subject, Long dateInM) {
		byte[] secretBytes = new byte[32];
		SecureRandom random = new SecureRandom();
		random.nextBytes(secretBytes);
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(dateInM)).signWith(key, SignatureAlgorithm.HS512).compact();
	}

	public Boolean validateToken(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}

	public Set<String> extractRoles(String token) {
		Claims claims = extractAllClaims(token);
		return new HashSet<>(claims.get("roles", List.class));
	}

	public ResponseCookie getCleanJwtCookie() {
		ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/api").build();
		return cookie;
	}
	
	public boolean validateJwtToken(String authToken) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
	    try {
	      Jwts.parserBuilder().setSigningKey(key).build().parse(authToken);
	      return true;
	    } catch (MalformedJwtException e) {
	      logger.error("Invalid JWT token: {}", e.getMessage());
	    } catch (ExpiredJwtException e) {
	      logger.error("JWT token is expired: {}", e.getMessage());
	    } catch (UnsupportedJwtException e) {
	      logger.error("JWT token is unsupported: {}", e.getMessage());
	    } catch (IllegalArgumentException e) {
	      logger.error("JWT claims string is empty: {}", e.getMessage());
	    }

	    return false;
	  }
	
	 public String getJwtFromCookies(HttpServletRequest request) {
		    Cookie cookie = WebUtils.getCookie(request, jwtCookie);
		    if (cookie != null) {
		      return cookie.getValue();
		    } else {
		      return null;
		    }
		  }
}

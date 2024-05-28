package com.javalearnings.securitydemo.filters;

import com.javalearnings.securitydemo.constants.FilterExceptionReason;
import com.javalearnings.securitydemo.entities.auth.User;
import com.javalearnings.securitydemo.exceptions.FilterException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
@Slf4j
public class JwtTokenUtil {

	@Value("${app.jwt.expiration}")
	private long EXPIRE_DURATION;
	
	@Value("${app.jwt.secret}")
	private String SECRET_KEY;
	
	public String generateAccessToken(User user) {
		return Jwts.builder()
				.setSubject(String.format("%s,%s", user.getUserId(), user.getUsername()))
				.setIssuer("CodeJava")
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public boolean validateAccessToken(String token, Integer userId) {
		try {
			final Integer extractedUserId = getUserId(token);
			log.debug("extractedUserId {}", extractedUserId);
			log.debug("userId {}", userId);
			if (extractedUserId.equals(userId)) {
				Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
				return true;
			} else {
				throw new FilterException(FilterExceptionReason.MALFORMED_JWT_EXCEPTION, "MALFORMED_JWT_EXCEPTION");
			}
		} catch (ExpiredJwtException ex) {
			log.error("JWT expired {}", ex.getMessage());
			throw new FilterException(FilterExceptionReason.EXPIRED_JWT_EXCEPTION, "EXPIRED_JWT_EXCEPTION");
		} catch (IllegalArgumentException ex) {
			log.error("Token is null, empty or only whitespace {}", ex.getMessage());
			throw new FilterException(FilterExceptionReason.ILLEGAL_ARGUMENT_EXCEPTION, "ILLEGAL_ARGUMENT_EXCEPTION");
		} catch (MalformedJwtException ex) {
			log.error("JWT is invalid {}", ex);
			throw new FilterException(FilterExceptionReason.MALFORMED_JWT_EXCEPTION, "MALFORMED_JWT_EXCEPTION");
		} catch (UnsupportedJwtException ex) {
			log.error("JWT is not supported {}", ex);
			throw new FilterException(FilterExceptionReason.UNSUPPORTED_JWT_EXCEPTION, "UNSUPPORTED_JWT_EXCEPTION");
		} catch (SignatureException ex) {
			log.error("Signature validation failed {}", ex);
			throw new FilterException(FilterExceptionReason.SIGNATURE_EXCEPTION, "SIGNATURE_EXCEPTION");
		} catch (Exception ex) {
			log.error("JWT Exception {}", ex);
			throw new FilterException(FilterExceptionReason.GENERIC_JWT_EXCEPTION, "GENERIC_JWT_EXCEPTION");
		}
	}
	
	public String getSubject(String token) {
		return parseClaims(token).getSubject();
	}
	
	private Claims parseClaims(String token) {
		return Jwts.parser()
				.setSigningKey(SECRET_KEY)
				.parseClaimsJws(token)
				.getBody();
	}

	private Integer getUserId(String token) {
		String[] jwtSubject = getSubject(token).split(",");
		log.debug("JWT Subject {}", jwtSubject);
		return Integer.valueOf(jwtSubject[0]);
	}
}

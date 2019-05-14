package id.bts.userShopping.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	public String getUsernameFromToken(String token) {
		String username = null;
		try {

			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();

		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims = null;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	public boolean validateToken(String authToken) {
		return !isTokenExpired(authToken);
	}

	private boolean isTokenExpired(String authToken) {
		final Date expiration = getExpirationDateFromToken(authToken);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromToken(String authToken) {
		Date expiration = null;
		try {
			final Claims claims = getClaimsFromToken(authToken);
			if (claims != null) {
				expiration = claims.getExpiration();
			} else {
				expiration = null;
			}

		} catch (Exception e) {
			expiration = null;
		}

		return expiration;
	}

	public String generateToken(JwtUser userDetails) {
		Map<String, Object> customClaims = new HashMap<>();
		return generateToken(customClaims, userDetails);
	}

	private String generateToken(Map<String, Object> customClaims, JwtUser userDetails) {

		return Jwts.builder().setSubject(userDetails.getUsername()).setExpiration(generateExpirationDate()).signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
}

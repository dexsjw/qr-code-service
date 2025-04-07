package challenge.tech.crud_auth.qr_code_service.security;

import challenge.tech.crud_auth.qr_code_service.entity.UserEntity;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtTokenUtil {

    private final SecretKey secretKey = Jwts.SIG.HS256.key().build();
    private final String JWT_TOKEN_PREFIX = "Bearer ";

    @Value("$jwt.session.period.milliseconds: 3600000")
    private long jwtSessionPeriod;

    public String createJwtToken(UserEntity user) {
        Date tokenIssuedAtDate = new Date();
        Date tokenExpirationDate = new Date(tokenIssuedAtDate.getTime() + jwtSessionPeriod);

        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(tokenIssuedAtDate)
                .expiration(tokenExpirationDate)
                .signWith(secretKey)
                .compact();
    }

    public Claims parseJwtToken(String jwtToken) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(jwtToken);
            return jws.getPayload();
        } catch (UnsupportedJwtException ex) {
            throw new UnsupportedJwtException("JWT is not a signed Claims");
        } catch (JwtException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String resolveJwtToken(HttpServletRequest request) {
        String bearerJwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerJwtToken != null && bearerJwtToken.startsWith(JWT_TOKEN_PREFIX)) {
            return bearerJwtToken.substring(JWT_TOKEN_PREFIX.length());
        }
        return null;
    }

    public String getUsername(Claims claims) {
        return claims.getSubject();
    }

}

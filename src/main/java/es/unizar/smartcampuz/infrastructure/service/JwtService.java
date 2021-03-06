package es.unizar.smartcampuz.infrastructure.service;

import es.unizar.smartcampuz.infrastructure.auth.Credential;
import es.unizar.smartcampuz.infrastructure.auth.jwt.SecretKeyProvider;
import es.unizar.smartcampuz.infrastructure.auth.CredentialRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.ZoneOffset.UTC;

@Component
public class JwtService {
    private static final String ISSUER = "SmartCampUZ";
    private SecretKeyProvider secretKeyProvider;
    @Autowired
    private CredentialRepository credentialRepository;
    private static final Logger LOG = LoggerFactory
        .getLogger(JwtService.class);

    @SuppressWarnings("unused")
    public JwtService() {
        this(null);
    }

    @Autowired
    public JwtService(SecretKeyProvider secretKeyProvider) {
        this.secretKeyProvider = secretKeyProvider;
    }

    public String tokenFor(Credential credential) throws IOException, URISyntaxException{
        byte[] secretKey = secretKeyProvider.getKey();
        Date expiration = Date.from(LocalDateTime.now().plusHours(2).toInstant(UTC));
        return Jwts.builder()
            .setSubject(credential.getEmail())
            .setExpiration(expiration)
            .setIssuer(ISSUER)
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public Credential verify(String token) throws IOException, URISyntaxException{
        byte[] secretKey = secretKeyProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        return credentialRepository.findByEmail(claims.getBody().getSubject().toString());
    }
}

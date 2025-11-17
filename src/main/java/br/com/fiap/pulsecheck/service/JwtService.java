package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.config.JwtProperties;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties props;
    private final Algorithm algorithm;

    public JwtService(JwtProperties props) {
        this.props = props;
        this.algorithm = Algorithm.HMAC256(props.getSecret());
    }

    public String generateToken(String email) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + props.getExpiration());

        return JWT.create()
                .withIssuer(props.getIssuer())
                .withSubject(email)
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(algorithm);
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(algorithm).withIssuer(props.getIssuer()).build().verify(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String extractEmail(String token) {
        return JWT.require(algorithm).withIssuer(props
                .getIssuer())
                .build()
                .verify(token)
                .getSubject();
    }
}

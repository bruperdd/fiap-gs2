package br.com.fiap.pulsecheck.service;

import br.com.fiap.pulsecheck.config.JwtProperties;
import br.com.fiap.pulsecheck.model.Users;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    public String generateToken(Users user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + props.getExpiration());

        return JWT.create()
                .withIssuer(props.getIssuer())
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .withClaim("id", user.getId())
                .withClaim("department_id", user.getDepartment_id())
                .withClaim("name", user.getName())
                .withClaim("role", user.getRole())
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

    public Users extractUser(String token) {
        DecodedJWT jwt = JWT.require(algorithm)
                .withIssuer(props.getIssuer())
                .build()
                .verify(token);

        int id = jwt.getClaim("id").asInt();
        int department_id = jwt.getClaim("department_id").asInt();
        String name = jwt.getClaim("name").asString();
        String role = jwt.getClaim("role").asString();

        return new Users(id, department_id, name, role);
    }
}

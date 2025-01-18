package br.com.forumhub.Razera.Infra.security;

import br.com.forumhub.Razera.Principal.usuario.Usuarios;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.logging.Logger;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    private static final String ISSUER = "Forum.HUB API";  // Constante para o issuer
    private static final Logger logger = Logger.getLogger(TokenService.class.getName());

    /**
     * Cria um token JWT para o usuário.
     * @param user O usuário para o qual o token será gerado.
     * @return O token JWT gerado.
     */
    public String createToken(Usuarios user) {
        try {
            Algorithm algorithm = getAlgorithm();
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            logger.severe("Erro ao criar o token JWT: " + exception.getMessage());
            throw new TokenCreationException("Erro ao criar o token JWT", exception);
        }
    }

    /**
     * Retorna o subject (usuário) de um token JWT.
     * @param tokenJWT O token JWT.
     * @return O subject (usuário) contido no token.
     */
    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = getAlgorithm();
            return verifyToken(tokenJWT, algorithm);
        } catch (JWTVerificationException exception) {
            logger.warning("Token JWT inválido ou expirado: " + exception.getMessage());
            throw new TokenVerificationException("Token JWT inválido ou expirado!", exception);
        }
    }

    /**
     * Retorna o algoritmo de criptografia para criação e verificação de tokens.
     * @return O algoritmo de criptografia.
     */
    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    /**
     * Verifica o token JWT e retorna o subject.
     * @param tokenJWT O token JWT.
     * @param algorithm O algoritmo de criptografia.
     * @return O subject do token JWT.
     */
    private String verifyToken(String tokenJWT, Algorithm algorithm) {
        return JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build()
                .verify(tokenJWT)
                .getSubject();
    }

    /**
     * Retorna a data de expiração do token (2 horas após a criação).
     * @return A data de expiração do token.
     */
    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.ofHours(-3));
    }
}

/**
 * Exceção personalizada para falhas ao criar o token.
 */
class TokenCreationException extends RuntimeException {
    public TokenCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

/**
 * Exceção personalizada para falhas na verificação do token.
 */
class TokenVerificationException extends RuntimeException {
    public TokenVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}

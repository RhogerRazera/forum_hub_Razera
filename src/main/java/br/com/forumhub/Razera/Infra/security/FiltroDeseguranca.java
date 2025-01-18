package br.com.forumhub.Razera.Infra.security;

import br.com.forumhub.Razera.Principal.usuario.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class FiltroDeseguranca extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    private static final Logger logger = Logger.getLogger(FiltroDeseguranca.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);

        if (token != null) {
            Optional<String> subject = Optional.ofNullable(tokenService.getSubject(token));
            subject.ifPresentOrElse(sub -> authenticateUser(sub),
                    () -> logger.warning("Token inválido ou não encontrado"));
        } else {
            logger.info("Token não fornecido no cabeçalho de autorização.");
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Recupera o token JWT do cabeçalho Authorization da requisição.
     * @param request A requisição HTTP.
     * @return O token JWT ou null caso não seja encontrado.
     */
    private String recoverToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

    /**
     * Autentica o usuário com base no subject (email) extraído do token.
     * @param subject O subject (email) do token.
     */
    private void authenticateUser(String subject) {
        var user = repository.findByEmail(subject);

        if (user != null) {
            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } else {
            logger.warning("Usuário não encontrado para o token com subject: " + subject);
        }
    }
}

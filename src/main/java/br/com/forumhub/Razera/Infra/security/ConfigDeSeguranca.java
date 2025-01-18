package br.com.forumhub.Razera.Infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class ConfigDeSeguranca {

    @Autowired
    private FiltroDeseguranca filtroDeseguranca;

    /**
     * Configurações de segurança da aplicação.
     * Define quais URLs são permitidas sem autenticação e quais exigem autenticação.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable) // Desativa a proteção CSRF para APIs REST
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Utiliza sessão sem estado (stateless)
                .authorizeRequests(req -> {
                    // Permite acesso irrestrito a rotas de usuário e Swagger
                    req.requestMatchers("/usuarios/**").permitAll(); // Talvez você queira restringir essa rota dependendo do caso
                    req.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll(); // Swagger API
                    req.anyRequest().authenticated(); // Requer autenticação para qualquer outra rota
                })
                .addFilterBefore(filtroDeseguranca, UsernamePasswordAuthenticationFilter.class) // Adiciona o filtro de segurança customizado antes do filtro de autenticação
                .build();
    }

    /**
     * Configuração do AuthenticationManager para autenticação personalizada.
     * @param configuration Configuração de autenticação do Spring Security.
     * @return O AuthenticationManager configurado.
     * @throws Exception Caso haja falha na configuração.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Bean para codificação de senhas utilizando BCrypt.
     * @return O PasswordEncoder configurado.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

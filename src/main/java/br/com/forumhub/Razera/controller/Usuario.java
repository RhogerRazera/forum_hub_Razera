package br.com.forumhub.Razera.controller;

import br.com.forumhub.Razera.Principal.usuario.*;
import br.com.forumhub.domain.usuario.*;
import br.com.forumhub.Razera.Infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    // Endpoint para registrar um novo usuário
    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailDAO> registerUser(@RequestBody @Valid UserDataReceiverDTO data, UriComponentsBuilder uriBuilder) {
        // Verifica se o email já está cadastrado
        if (repository.existsByEmail(data.getEmail())) {
            return ResponseEntity.badRequest().body(new UserDetailDAO("Email já cadastrado", null));
        }

        // Cria e persiste a entidade Usuario
        var user = new Usuarios(data);
        repository.save(user);

        // Cria o URI de localização
        var location = uriBuilder.path("/usuarios/{id}").buildAndExpand(user.getId()).toUri();

        // Retorna a resposta com os dados do usuário
        return ResponseEntity.created(location).body(new UserDetailDAO(user.getName(), user.getEmail()));
    }

    // Endpoint para autenticar o usuário e gerar token JWT
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid UserLoginDataReceiverDTO loginData) {
        // Cria o token de autenticação com as credenciais fornecidas
        var tokenSecurity = new UsernamePasswordAuthenticationToken(loginData.email(), loginData.password());
        var authentication = authenticationManager.authenticate(tokenSecurity);

        // Gera o token JWT
        var tokenJWT = tokenService.createToken((Usuarios) authentication.getPrincipal());

        return ResponseEntity.ok(tokenJWT);
    }
}

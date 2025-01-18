package br.com.forumhub.Razera.Principal.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Usuarios, Long> {
    UserDetails findByEmail(String email);
}

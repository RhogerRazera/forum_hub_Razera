package br.com.forumhub.Razera.Principal.usuario;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDataReceiverDTO(
        @NotBlank
        String email,
        @NotBlank
        @JsonAlias("senha") String password) {
}

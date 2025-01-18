package br.com.forumhub.Razera.Principal.respostas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtualizaçãoDasRespostas(
        @NotNull
        Long id,
        @JsonProperty("mensagem")
        @NotBlank
        String message
) {
}

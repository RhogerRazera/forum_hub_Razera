package br.com.forumhub.Razera.Principal.topicos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicUpdatingDataReceiverDTO(
        @NotNull
        Long id,
        @JsonAlias("titulo")
        @NotBlank
        String title,
        @JsonAlias("mensagem")
        @NotBlank
        String message
        ) {
}

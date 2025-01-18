package br.com.forumhub.Razera.Principal.respostas;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RecebeResposta(
        @JsonProperty("mensagem")
        @NotBlank
        String message,
        @JsonProperty("data_criacao")
        @NotNull
        @FutureOrPresent
        LocalDateTime creationDate,
        @JsonProperty("topico")
        @NotNull
        Long topic
) {
}

package br.com.forumhub.Razera.Principal.topicos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record TopicDisplayDAO(
        Long id,
        @JsonProperty("titulo")
        String title,
        @JsonProperty("mensagem")
        String message,
        @JsonProperty("data_criacao")
        LocalDateTime creationDate,
        Status status,
        @JsonProperty("autor")
        String author) {
}

package br.com.forumhub.Razera.Principal.respostas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ExibirResposta(
        Long id,
        @JsonProperty("mensagem")
        String message,
        @JsonProperty("data_criacao")
        LocalDateTime creationDate,
        @JsonProperty("topico")
        Long topic
) {
    public ExibirResposta(Respostas a) {
        this(a.getId(), a.getMessage(), a.getCreationDate(), a.getTopic().getId());
    }
}

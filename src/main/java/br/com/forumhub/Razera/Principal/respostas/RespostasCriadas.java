package br.com.forumhub.Razera.Principal.respostas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record RespostasCriadas(
        @JsonProperty("mensagem")
        String message,
        @JsonProperty("data_criacao")
        LocalDateTime creationDate,
        @JsonProperty("topico")
        String topic,
        @JsonProperty("autor")
        String author) {
    public RespostasCriadas(Respostas respostas) {
        this(respostas.getMessage(), respostas.getCreationDate(), respostas.getTopic().getTitle(), respostas.getAuthor().getName());
    }
}

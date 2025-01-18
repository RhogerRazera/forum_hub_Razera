package br.com.forumhub.Razera.Principal.respostas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ExibirDetalhes(
        @JsonProperty("mensagem")
        String message,
        @JsonProperty("data_criacao")
        LocalDateTime creationDate,
        @JsonProperty("topico")
        Long topic,
        @JsonProperty("autor")
        Long author,
        @JsonProperty("titulo_topico")
        String topicTitle,
        @JsonProperty("nome_autor")
        String authorName,
        @JsonProperty("solucao")
        boolean solution
) {
    public ExibirDetalhes(Respostas respostas) {
        this(respostas.getMessage(), respostas.getCreationDate(), respostas.getTopic().getId(), respostas.getAuthor().getId(),
                respostas.getTopic().getTitle(), respostas.getAuthor().getName(), respostas.isSolution());
    }
}

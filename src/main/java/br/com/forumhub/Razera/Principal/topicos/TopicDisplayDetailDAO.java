package br.com.forumhub.Razera.Principal.topicos;

import br.com.forumhub.Razera.Principal.respostas.ExibirResposta;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public record TopicDisplayDetailDAO(
        @JsonProperty("titulo")
        String title,
        @JsonProperty("mensagem")
        String message,
        @JsonProperty("data_criacao")
        LocalDateTime creationDate,
        Status status,
        @JsonProperty("autor")
        String author,
        List<ExibirResposta> answers) {
        public TopicDisplayDetailDAO(Topic topic) {
                this(topic.getTitle(), topic.getMessage(), topic.getCreationDate(), topic.getStatus(), topic.getAuthor().getName(), topic.getRespostas());
        }
}

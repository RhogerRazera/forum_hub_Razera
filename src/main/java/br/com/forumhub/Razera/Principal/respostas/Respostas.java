package br.com.forumhub.Razera.Principal.respostas;

import br.com.forumhub.Razera.Principal.topicos.Topic;
import br.com.forumhub.Razera.Principal.usuario.Usuarios;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "respostas")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Respostas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mensagem")
    private String message;
    @Column(name = "data_criacao")
    private LocalDateTime creationDate;
    @Setter
    @Column(name = "solucao")
    private boolean solution;

    @Setter
    @ManyToOne
    @JoinColumn(name = "autor")
    private Usuarios author;

    @Setter
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "topico")
    private Topic topic;

    public Respostas(RecebeResposta data, Usuarios author, Topic topic) {
        this.message = data.message();
        this.creationDate = data.creationDate();
        this.author = author;
        this.topic = topic;
    }

    public void updateAnswer(AtualizaçãoDasRespostas data) {
        this.message = data.message();
    }
}

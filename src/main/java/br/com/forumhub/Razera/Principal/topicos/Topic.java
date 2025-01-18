package br.com.forumhub.Razera.Principal.topicos;

import br.com.forumhub.Razera.Principal.respostas.Respostas;
import br.com.forumhub.Razera.Principal.respostas.ExibirResposta;
import br.com.forumhub.Razera.Principal.usuario.Usuarios;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "topicos")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "titulo")
    private String title;
    @Column(name = "mensagem")
    private String message;
    @Column(name = "data_criacao")
    private LocalDateTime creationDate;
    @Setter
    @Enumerated(EnumType.STRING)
    private Status status;

    @Setter
    @ManyToOne
    @JoinColumn(name = "autor")
    private Usuarios author;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Respostas> respostas = new ArrayList<>();

    public Topic(TopicDataReceiverDTO data) {
        this.title = data.title();
        this.message = data.message();
        this.creationDate = data.creationDate();
        this.status = Status.NAO_RESPONDIDO;
    }

    public void updateTopic(TopicUpdatingDataReceiverDTO data) {
        this.title = data.title();
        this.message = data.message();
    }

    public List<ExibirResposta> getRespostas() {
        return respostas.stream().map(ExibirResposta::new).toList();
    }
}

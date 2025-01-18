package br.com.forumhub.Razera.Principal.respostas;

import br.com.forumhub.Razera.Principal.topicos.Status;
import br.com.forumhub.Razera.Principal.topicos.TopicRepository;
import br.com.forumhub.Razera.Principal.usuario.Usuarios;
import br.com.forumhub.Razera.Infra.excecao.ExcecaoRespondida;
import br.com.forumhub.Razera.Infra.excecao.ExcecaoDeModi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ServiceRes {
    @Autowired
    private ResRepository resRepository;

    @Autowired
    private TopicRepository topicRepository;

    public Respostas createAnswer(RecebeResposta data) {
        var topic = topicRepository.getReferenceById(data.topic());

        var CONTEXT_AUTHOR = (Usuarios) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (topic.getStatus().equals(Status.RESPONDIDO) || topic.getStatus().equals(Status.FECHADO)) {
            throw new ExcecaoRespondida("Esse tópico já foi respondido ou está fechado!");
        }

        return new Respostas(data, CONTEXT_AUTHOR, topic);
    }

    public void setAsSolution(Respostas respostas) {
        respostas.setSolution(true);
        var topic = topicRepository.getReferenceById(respostas.getTopic().getId());
        if (respostas.isSolution()) {
            topic.setStatus(Status.RESPONDIDO);
        }
    }

    public void validateAuthorAuthorizationUpdate(Respostas respostas) {
        var CONTEXT_AUTHOR = (Usuarios) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!respostas.getAuthor().getEmail().equals(CONTEXT_AUTHOR.getEmail())) {
            throw new ExcecaoDeModi("Você não pode atualizar/modificar essa resposta!");
        }
    }

    public void validateAuthorAuthorizationDelete(Respostas respostas) {
        var CONTEXT_AUTHOR = (Usuarios) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!respostas.getAuthor().getEmail().equals(CONTEXT_AUTHOR.getEmail())) {
            if (!CONTEXT_AUTHOR.isAdmin()) {
                throw new ExcecaoDeModi("Você não pode deletar essa resposta!");
            }
        }
    }
}

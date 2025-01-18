package br.com.forumhub.Razera.controller;

import br.com.forumhub.Razera.Principal.topicos.*;
import br.com.forumhub.domain.topicos.*;
import br.com.forumhub.Razera.Principal.usuario.Usuarios;
import br.com.forumhub.Razera.Infra.excecao.ExcecaoDeModi;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
    @Autowired
    private TopicRepository topicRepository;

    @PostMapping
    @Transactional
    public ResponseEntity createTopic(@RequestBody @Valid TopicDataReceiverDTO data, UriComponentsBuilder uriBuilder) {
        var topic = new Topic(data);
        var CONTEXT_AUTHOR = (Usuarios) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        topic.setAuthor(CONTEXT_AUTHOR);
        topicRepository.save(topic);
        var location = uriBuilder.path("/topicos/{id}").buildAndExpand(topic.getId()).toUri();
        return ResponseEntity.created(location).body(new TopicCreatedDAO(topic));
    }

    @GetMapping
    public ResponseEntity<Page<TopicDisplayDAO>> displayTopics(@PageableDefault(sort = {"creationDate"}, direction = Sort.Direction.ASC) Pageable pageable) {
        var topics = topicRepository.findAllJoiningNameAuthor(pageable);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{idTopic}")
    public ResponseEntity detailTopic(@PathVariable Long idTopic) {
        var topic = topicRepository.getReferenceById(idTopic);
        return ResponseEntity.ok(new TopicDisplayDetailDAO(topic));
    }

    @PutMapping
    @Transactional
    public ResponseEntity updateTopic(@RequestBody @Valid TopicUpdatingDataReceiverDTO data) {
        var topic = topicRepository.getReferenceById(data.id());
        var CONTEXT_AUTHOR = (Usuarios) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!topic.getAuthor().getEmail().equals(CONTEXT_AUTHOR.getEmail())) {
            throw new ExcecaoDeModi("Você não pode atualizar/modificar esse tópico!");
        }
        topic.updateTopic(data);
        return ResponseEntity.ok(new TopicDisplayDetailDAO(topic));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deleteTopic(@PathVariable Long id) {
        var topic = topicRepository.getReferenceById(id);
        var CONTEXT_AUTHOR = (Usuarios) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!topic.getAuthor().getEmail().equals(CONTEXT_AUTHOR.getEmail())) {
            if (!CONTEXT_AUTHOR.isAdmin()) {
                throw new ExcecaoDeModi("Você não pode deletar essa resposta!");
            }
        }
        topicRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

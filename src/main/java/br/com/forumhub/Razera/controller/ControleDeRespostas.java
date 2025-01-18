import br.com.forumhub.Razera.Principal.respostas.*;
import br.com.forumhub.Razera.controller.Resposta;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/respostas")
public class ControleDeRespostas {

    @Autowired
    private ServiceRes serviceRes;

    @Autowired
    private ResRepository resRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<RespostasCriadas> createAnswer(@RequestBody @Valid RecebeResposta data, UriComponentsBuilder uriBuilder) {
        var answer = serviceRes.createAnswer(data);
        resRepository.save(answer);
        var location = uriBuilder.path("/respostas/{id}").buildAndExpand(answer.getId()).toUri();
        return ResponseEntity.created(location).body(new RespostasCriadas(answer));
    }

    @GetMapping("/solution/{id}")
    @Transactional
    public ResponseEntity<Void> markAnswerAsSolution(@PathVariable Long id) {
        Resposta answer = findAnswerById(id);
        serviceRes.setAsSolution(answer);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<ExibirResposta>> displayAnswers(@PageableDefault(sort = {"creationDate"}, direction = Sort.Direction.ASC) SpringDataWebProperties.Pageable pageable) {
        Page<ExibirResposta> answers = resRepository.findAll((Pageable) pageable).map(ExibirResposta::new);
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/topico/{id}")
    public ResponseEntity<Page<ExibirDetalhes>> displayAllAnswersByTopicId(@PathVariable Long id, @PageableDefault(sort = {"creationDate"}, direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ExibirDetalhes> answers = resRepository.findAllByTopicId(id, pageable).map(ExibirDetalhes::new);
        if (answers.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma resposta encontrada para o tópico com id " + id);
        }
        return ResponseEntity.ok(answers);
    }

    @GetMapping("/{idAnswer}")
    public ResponseEntity<ExibirDetalhes> detailAnswer(@PathVariable Long idAnswer) {
        Respostas answer = findAnswerById(idAnswer);
        return ResponseEntity.ok(new ExibirDetalhes(answer));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<ExibirDetalhes> updateAnswer(@RequestBody @Valid AtualizaçãoDasRespostas data) {
        Respostas answer = findAnswerById(data.id());
        serviceRes.validateAuthorAuthorizationUpdate(answer);
        answer.updateAnswer(data);
        return ResponseEntity.ok(new ExibirDetalhes(answer));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        Resposta answer = findAnswerById(id);
        serviceRes.validateAuthorAuthorizationDelete(answer);
        resRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Método auxiliar para evitar repetição de código
    private Resposta findAnswerById(Long id) {
        return resRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada com id " + id));
    }
}

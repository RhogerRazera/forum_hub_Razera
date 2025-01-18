package br.com.forumhub.Razera.Infra.excecao;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ManiExcecao {

    // Exceção para violação de restrição de integridade no banco de dados
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> sqlConstraintIntegrityViolation(SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de integridade de dados: " + ex.getMessage());
    }

    // Exceção customizada de tópico já respondido
    @ExceptionHandler(ExcecaoRespondida.class)
    public ResponseEntity<String> topicAlreadyAnsweredHandler(ExcecaoRespondida ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Tópico já foi respondido: " + ex.getMessage());
    }

    // Exceção para campos inválidos na validação de métodos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionValidationData>> argumentNotValidHandler(MethodArgumentNotValidException ex) {
        List<ExceptionValidationData> fieldErrors = ex.getFieldErrors().stream()
                .map(ExceptionValidationData::new)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrors);
    }

    // Exceção de modificação não autorizada
    @ExceptionHandler(ExcecaoDeModi.class)
    public ResponseEntity<String> unauthorizedModification(ExcecaoDeModi ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Modificação não autorizada: " + ex.getMessage());
    }

    // Exceção de entidade não encontrada
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidade não encontrada.");
    }

    // Exceção genérica para outras falhas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado: " + ex.getMessage());
    }

    // Classe interna para armazenar os dados de validação dos campos
    public static class ExceptionValidationData {
        private final String campo;
        private final String mensagem;

        public ExceptionValidationData(FieldError e) {
            this.campo = e.getField();
            this.mensagem = e.getDefaultMessage();
        }

        public String getCampo() {
            return campo;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}

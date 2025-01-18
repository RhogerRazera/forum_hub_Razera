package br.com.forumhub.Razera.Infra.excecao;

public class ExcecaoRespondida extends RuntimeException {
    public ExcecaoRespondida(String message) {
        super(message);
    }
}

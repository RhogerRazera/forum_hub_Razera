package br.com.forumhub.Razera.Principal.usuario;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDetailDAO(@JsonProperty("nome") String name, String email) {
}

package br.com.forumhub.Razera.Principal.respostas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResRepository extends JpaRepository<Respostas, Long> {
    Page<Respostas> findAllByTopicId(Long id, Pageable pageable);
}

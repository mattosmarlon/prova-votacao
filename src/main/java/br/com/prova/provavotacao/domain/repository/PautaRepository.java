package br.com.prova.provavotacao.domain.repository;

import br.com.prova.provavotacao.domain.entity.Pauta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository  extends CrudRepository<Pauta, Integer> {
}

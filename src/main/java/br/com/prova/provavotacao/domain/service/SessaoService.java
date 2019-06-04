package br.com.prova.provavotacao.domain.service;

import br.com.prova.provavotacao.domain.dto.SessaoDto;
import br.com.prova.provavotacao.domain.dto.create.SessaoCreateDto;

public interface SessaoService {

    SessaoDto getById(Integer id);

    SessaoDto create(SessaoCreateDto sessaoCreateDto);

    Boolean aberta(Integer id);

    void delete(Integer id);
}

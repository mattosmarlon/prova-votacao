package br.com.prova.provavotacao.domain.service;

import br.com.prova.provavotacao.domain.dto.PautaDto;
import br.com.prova.provavotacao.domain.dto.create.PautaCreateDto;

public interface PautaService {

    PautaDto getById(Integer id);

    PautaDto create(PautaCreateDto pautaCreateDto);

    void delete(Integer id);
}

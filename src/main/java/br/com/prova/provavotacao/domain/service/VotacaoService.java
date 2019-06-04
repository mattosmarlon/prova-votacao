package br.com.prova.provavotacao.domain.service;

import br.com.prova.provavotacao.domain.dto.VotoDto;
import br.com.prova.provavotacao.domain.dto.resultado.PautaResultadoDto;

public interface VotacaoService {

    void votar(Integer sessaoId, VotoDto votoDto);

    PautaResultadoDto getResultadoVotacao(Integer pautaId);
}

package br.com.prova.provavotacao.infrastructure.service;

import br.com.prova.provavotacao.domain.dto.VotoDto;
import br.com.prova.provavotacao.domain.dto.resultado.PautaResultadoDto;
import br.com.prova.provavotacao.domain.entity.Votacao;
import br.com.prova.provavotacao.domain.repository.VotacaoRepository;
import br.com.prova.provavotacao.domain.service.PautaService;
import br.com.prova.provavotacao.domain.service.SessaoService;
import br.com.prova.provavotacao.domain.service.VotacaoService;
import br.com.prova.provavotacao.infrastructure.exception.IntegridadeException;
import br.com.prova.provavotacao.infrastructure.exception.NegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.time.Instant;

public class VotacaoServiceImpl implements VotacaoService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotacaoRepository repository;

    @Override
    public void votar(Integer sessaoId, VotoDto votoDto) {
        logger.info("Recebeu um voto da sessão {}: {}", sessaoId, votoDto);
        if (votoDto.getCodigoAssociado() == null || votoDto.getCodigoAssociado() == 0) {
            throw new IntegridadeException("Código do associado é obrigatório");
        }
        if (sessaoId == null || sessaoId == 0) {
            throw new IntegridadeException("Código da sessão é obrigatório");
        }
        if (!sessaoService.aberta(sessaoId)) {
            throw new NegocioException("Sessão está fechada para votação.");
        }
        if (!associadoHabilitado(sessaoId, votoDto.getCodigoAssociado())) {
            throw new NegocioException("Associado já votou nessa sessão.");
        }
        Votacao voto = new Votacao();
        voto.setCodigoAssociado(votoDto.getCodigoAssociado());
        voto.setCodigoSessao(sessaoId);
        voto.setOpcao(votoDto.getOpcao().toString());
        voto.setDataCadastro(Date.from(Instant.now()));
        repository.save(voto);
        logger.info("Votação adicionada com sucesso: {}", voto.getCodigo());
    }

    public PautaResultadoDto getResultadoVotacao(Integer pautaId) {
        logger.info("Consultando resultado da votação: {}", pautaId);
        return new PautaResultadoDto(
                pautaService.getById(pautaId).getDescricao(),
                repository.countResultados(pautaId));
    }

    private Boolean associadoHabilitado(Integer sessaoId, Integer codigoAssociado) {
        return !repository.existsByCodigoSessaoAndCodigoAssociado(sessaoId, codigoAssociado);
    }
}

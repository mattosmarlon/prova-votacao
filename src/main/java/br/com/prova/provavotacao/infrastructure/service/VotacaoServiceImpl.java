package br.com.prova.provavotacao.infrastructure.service;

import br.com.prova.provavotacao.domain.dto.UsuarioHabilitadoDto;
import br.com.prova.provavotacao.domain.dto.VotoDto;
import br.com.prova.provavotacao.domain.dto.resultado.PautaResultadoDto;
import br.com.prova.provavotacao.domain.entity.Votacao;
import br.com.prova.provavotacao.domain.repository.VotacaoRepository;
import br.com.prova.provavotacao.domain.service.PautaService;
import br.com.prova.provavotacao.domain.service.SessaoService;
import br.com.prova.provavotacao.domain.service.VotacaoService;
import br.com.prova.provavotacao.infrastructure.exception.IntegridadeException;
import br.com.prova.provavotacao.infrastructure.exception.NegocioException;
import br.com.prova.provavotacao.infrastructure.facade.UserInfoFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

@Service
public class VotacaoServiceImpl implements VotacaoService {

    private static final String USER_ABLE_TO_VOTE = "ABLE_TO_VOTE";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private PautaService pautaService;

    @Autowired
    private VotacaoRepository repository;

    @Autowired
    private UserInfoFacade userInfoFacade;

    @Autowired
    private NotificacaoResultadoService notificacaoResultadoService;

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
        if (!cpfHabilitadoParaVotacao(votoDto.getCpf())) {
            throw new NegocioException("Associado já está apto a votação ou CPF informado não é válido.");
        }
        Votacao voto = new Votacao();
        voto.setCodigoAssociado(votoDto.getCodigoAssociado());
        voto.setCodigoSessao(sessaoId);
        voto.setOpcao(votoDto.getOpcao().toString());
        voto.setDataCadastro(Date.from(Instant.now()));
        repository.save(voto);
        logger.info("Votação adicionada com sucesso: {}", voto.getCodigo());
    }

    public Boolean cpfHabilitadoParaVotacao(String cpf) {
        Optional<UsuarioHabilitadoDto> usuarioHabilitadoDto = userInfoFacade.usuarioHabilitado(cpf);
        if (!usuarioHabilitadoDto.isPresent()) {
            return false;
        }
        return USER_ABLE_TO_VOTE.equals(usuarioHabilitadoDto.get().getStatus());
    }

    public PautaResultadoDto getResultadoVotacao(Integer pautaId) {
        logger.info("Consultando resultado da votação: {}", pautaId);
        PautaResultadoDto resultadoDto =
                new PautaResultadoDto(
                        pautaService.getById(pautaId).getDescricao(),
                        repository.countResultados(pautaId));
        notificacaoResultadoService.notificar(resultadoDto);
        return resultadoDto;
    }

    private Boolean associadoHabilitado(Integer sessaoId, Integer codigoAssociado) {
        return !repository.existsByCodigoSessaoAndCodigoAssociado(sessaoId, codigoAssociado);
    }
}

package br.com.prova.provavotacao.infrastructure.service;

import br.com.prova.provavotacao.domain.dto.SessaoDto;
import br.com.prova.provavotacao.domain.dto.create.SessaoCreateDto;
import br.com.prova.provavotacao.domain.entity.Sessao;
import br.com.prova.provavotacao.domain.repository.SessaoRepository;
import br.com.prova.provavotacao.domain.service.SessaoService;
import br.com.prova.provavotacao.infrastructure.converter.SessaoConverter;
import br.com.prova.provavotacao.infrastructure.exception.IntegridadeException;
import br.com.prova.provavotacao.infrastructure.exception.ObjetoNotFoundException;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;

@Service
public class SessaoServiceImpl implements SessaoService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SessaoRepository repository;

    @Autowired
    private SessaoConverter converter;

    @Override
    public SessaoDto getById(Integer id) {
        logger.info("Consultando a sessão: {}", id);
        Sessao sessao = repository.findById(id).orElse(null);
        if (sessao == null) {
            throw new ObjetoNotFoundException("Sessão não encontrada");
        }
        logger.info("Sessão encontrada: {}", sessao);
        return converter.convert(sessao);
    }

    @Override
    public SessaoDto create(SessaoCreateDto sessaoCreateDto) {
        logger.info("Criando uma nova sessão: {}", sessaoCreateDto);
        Sessao sessao = converter.convert(sessaoCreateDto);
        if (sessao.getCodigoPauta() == null || sessao.getCodigoPauta() == 0) {
            throw new IntegridadeException("Código da pauta é obrigatório.");
        }
        if (sessao.getDataAbertura() == null) {
            throw new IntegridadeException("Data de abertura é obrigatória.");
        }
        if (sessao.getDataEncerramento() == null) {
            // Duração padrão de 1min caso não seja informada data de encerramento.
            sessao.setDataEncerramento(DateUtils.addMinutes(sessao.getDataAbertura(), 1));
        }
        repository.save(sessao);
        logger.info("Sessão criada com sucesso: {}", sessao.getCodigo());
        return converter.convert(sessao);
    }

    @Override
    public Boolean aberta(Integer id) {
        return getById(id).getDataEncerramento().compareTo(Date.from(Instant.now())) > 0;
    }

    @Override
    public void delete(Integer id) {
        try {
            logger.info("Sessão a pauta: {}", id);
            repository.deleteById(id);
            logger.info("Sessão deletada com sucesso");
        } catch (DataIntegrityViolationException sql) {
            logger.error(sql.getMessage(), sql);
            throw new IntegridadeException("Esta Sessão possui itens dependentes.");
        }
    }
}

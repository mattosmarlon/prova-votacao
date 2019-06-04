package br.com.prova.provavotacao.infrastructure.service;


import br.com.prova.provavotacao.domain.dto.PautaDto;
import br.com.prova.provavotacao.domain.dto.create.PautaCreateDto;
import br.com.prova.provavotacao.domain.entity.Pauta;
import br.com.prova.provavotacao.domain.repository.PautaRepository;
import br.com.prova.provavotacao.domain.service.PautaService;
import br.com.prova.provavotacao.infrastructure.converter.PautaConverter;
import br.com.prova.provavotacao.infrastructure.exception.IntegridadeException;
import br.com.prova.provavotacao.infrastructure.exception.ObjetoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;

@Service
public class PautaServiceImpl implements PautaService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PautaRepository repository;

    @Autowired
    private PautaConverter converter;

    @Override
    public PautaDto getById(Integer id) {
        logger.info("Consulta da pauta: {}", id);
        Pauta pauta = repository.findById(id).orElse(null);
        if (pauta == null) {
            throw new ObjetoNotFoundException("Pauta não encontrada");
        }
        logger.info("Consulta da pauta executada com sucesso: {}", pauta);
        return converter.convert(pauta);
    }

    @Override
    public PautaDto create(PautaCreateDto pautaCreateDto) {
        logger.info("Criando nova pauta: {}", pautaCreateDto);
        Pauta pauta = converter.convert(pautaCreateDto);
        pauta.setDataCadastro(Date.from(Instant.now()));
        repository.save(pauta);
        logger.info("Pauta criada com sucesso: {}", pauta.getCodigo());
        return converter.convert(pauta);
    }

    @Override
    public void delete(Integer id) {
        try {
            logger.info("Deletando a pauta: {}", id);
            repository.deleteById(id);
            logger.info("Pauta deletada com sucesso");
        } catch (DataIntegrityViolationException sql) {
            logger.error(sql.getMessage(), sql);
            throw new IntegridadeException("Esta pauta possui itens dependentes.");
        }
    }
}
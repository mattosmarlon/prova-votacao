package br.com.prova.provavotacao.service;

import br.com.prova.provavotacao.domain.dto.SessaoDto;
import br.com.prova.provavotacao.domain.dto.create.SessaoCreateDto;
import br.com.prova.provavotacao.domain.entity.Sessao;
import br.com.prova.provavotacao.domain.repository.SessaoRepository;
import br.com.prova.provavotacao.infrastructure.converter.SessaoConverter;
import br.com.prova.provavotacao.infrastructure.exception.ObjetoNotFoundException;
import br.com.prova.provavotacao.infrastructure.service.SessaoServiceImpl;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class SessaoServiceTest {

    @InjectMocks
    private SessaoServiceImpl service;

    @Mock
    private SessaoRepository repository;

    @Spy
    private SessaoConverter converter;

    @Test
    public void createSessaoTest() {
        SessaoCreateDto createDto = new SessaoCreateDto();
        createDto.setCodigoPauta(1);
        createDto.setDataAbertura(Date.from(Instant.now()));
        createDto.setDataEncerramento(DateUtils.addDays(Date.from(Instant.now()), 5));
        SessaoDto sessaoDto = service.create(createDto);
        assertNotNull(sessaoDto);
    }

    @Test
    public void getByIdFoundedTest() {
        when(repository.findById(1)).thenReturn(getFakeSessao());
        SessaoDto sessaoDto = service.getById(1);
        assertNotNull(sessaoDto);
        assertNotNull(sessaoDto.getCodigo());
        assertNotNull(sessaoDto.getDataAbertura());
        assertNotNull(sessaoDto.getDataEncerramento());
    }

    @Test(expected = ObjetoNotFoundException.class)
    public void getByIdNotFoundedTest() {
        when(repository.findById(1)).thenReturn(getFakeSessao());
        service.getById(2);
    }

    @Test
    public void sessaoAbertaTest() {
        when(repository.findById(1)).thenReturn(getFakeSessao());
        assertTrue(service.aberta(1));
    }

    @Test(expected = ObjetoNotFoundException.class)
    public void sessaoAbertaNotFoundTest() {
        when(repository.findById(1)).thenReturn(getFakeSessao());
        assertTrue(service.aberta(2));
    }

    @Test
    public void sessaoFechadaTest() {
        Sessao sessao = getFakeSessao().get();
        sessao.setDataAbertura(DateUtils.addDays(Date.from(Instant.now()), -5));
        sessao.setDataEncerramento(DateUtils.addDays(Date.from(Instant.now()), -3));
        when(repository.findById(1)).thenReturn(Optional.of(sessao));
        assertFalse(service.aberta(1));
    }

    private Optional<Sessao> getFakeSessao() {
        Sessao sessao = new Sessao();
        sessao.setCodigo(1);
        sessao.setCodigoPauta(1);
        sessao.setDataAbertura(Date.from(Instant.now()));
        sessao.setDataEncerramento(DateUtils.addDays(Date.from(Instant.now()), 5));
        sessao.setDataCadastro(Date.from(Instant.now()));
        return Optional.of(sessao);
    }

    private SessaoDto getFakeSessaoDto() {
        SessaoDto sessao = new SessaoDto();
        sessao.setCodigo(1);
        sessao.setCodigoPauta(1);
        sessao.setDataAbertura(Date.from(Instant.now()));
        sessao.setDataEncerramento(DateUtils.addDays(Date.from(Instant.now()), 5));
        return sessao;
    }
}

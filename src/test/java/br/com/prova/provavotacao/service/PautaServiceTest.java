package br.com.prova.provavotacao.service;

import br.com.prova.provavotacao.domain.dto.PautaDto;
import br.com.prova.provavotacao.domain.dto.create.PautaCreateDto;
import br.com.prova.provavotacao.domain.entity.Pauta;
import br.com.prova.provavotacao.domain.repository.PautaRepository;
import br.com.prova.provavotacao.infrastructure.converter.PautaConverter;
import br.com.prova.provavotacao.infrastructure.exception.ObjetoNotFoundException;
import br.com.prova.provavotacao.infrastructure.service.PautaServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PautaServiceTest {

    @InjectMocks
    private PautaServiceImpl pautaService;

    @Mock
    private PautaRepository repository;

    @Spy
    private PautaConverter converter;

    @Test
    public void createPautaTest() {
        PautaCreateDto createDto = new PautaCreateDto();
        createDto.setDescricao("PAUTA DESCRIPTION");
        PautaDto pautaDto = pautaService.create(createDto);
        assertNotNull(pautaDto);
        assertNotNull(pautaDto.getDataCadastro());
    }

    @Test
    public void getByIdFoundedTest() {
        when(repository.findById(1)).thenReturn(getFakePauta());
        PautaDto pauta = pautaService.getById(1);
        assertNotNull(pauta);
        assertNotNull(pauta.getCodigo());
        assertNotNull(pauta.getDescricao());
        assertNotNull(pauta.getDataCadastro());
    }

    @Test(expected = ObjetoNotFoundException.class)
    public void getByIdNotFoundedTest() {
        when(repository.findById(1)).thenReturn(getFakePauta());
        pautaService.getById(2);
    }

    private Optional<Pauta> getFakePauta() {
        Pauta pauta = new Pauta();
        pauta.setDataCadastro(Date.from(Instant.now()));
        pauta.setDescricao("DESCRIÇÃO PAUTA");
        pauta.setCodigo(1);
        return Optional.of(pauta);
    }
}

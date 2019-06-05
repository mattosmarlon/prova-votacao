package br.com.prova.provavotacao.service;

import br.com.prova.provavotacao.domain.dto.PautaDto;
import br.com.prova.provavotacao.domain.dto.VotoDto;
import br.com.prova.provavotacao.domain.dto.enums.OpcaoVoto;
import br.com.prova.provavotacao.domain.dto.resultado.PautaResultadoDto;
import br.com.prova.provavotacao.domain.dto.resultado.VotacaoResultadoDto;
import br.com.prova.provavotacao.domain.repository.VotacaoRepository;
import br.com.prova.provavotacao.domain.service.PautaService;
import br.com.prova.provavotacao.domain.service.SessaoService;
import br.com.prova.provavotacao.infrastructure.converter.PautaConverter;
import br.com.prova.provavotacao.infrastructure.converter.SessaoConverter;
import br.com.prova.provavotacao.infrastructure.exception.IntegridadeException;
import br.com.prova.provavotacao.infrastructure.exception.NegocioException;
import br.com.prova.provavotacao.infrastructure.service.VotacaoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class VotacaoServiceTest {

    @InjectMocks
    private VotacaoServiceImpl service;

    @Mock
    private VotacaoRepository repository;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private PautaService pautaService;

    @Spy
    private PautaConverter pautaConverter;

    @Spy
    private SessaoConverter sessaoConverter;

    @Test(expected = IntegridadeException.class)
    public void votarAssociadoIntegridadeExceptionTest() {
        VotoDto votoDto = new VotoDto();
        votoDto.setOpcao(OpcaoVoto.Nao);
        service.votar(1, votoDto);
    }

    @Test(expected = IntegridadeException.class)
    public void votarSessaoIntegridadeExceptionTest() {
        VotoDto votoDto = new VotoDto();
        votoDto.setCodigoAssociado(1);
        votoDto.setOpcao(OpcaoVoto.Nao);
        service.votar(0, votoDto);
    }

    @Test(expected = NegocioException.class)
    public void votarAssociadoNaoHabilitadoTest() {
        VotoDto votoDto = new VotoDto();
        votoDto.setCodigoAssociado(1);
        votoDto.setOpcao(OpcaoVoto.Nao);
        when(sessaoService.aberta(1)).thenReturn(true);
        when(repository.existsByCodigoSessaoAndCodigoAssociado(1, 1)).thenReturn(true);
        service.votar(1, votoDto);
    }

    @Test
    public void votarSucessoTest() {
        VotoDto votoDto = new VotoDto();
        votoDto.setCodigoAssociado(1);
        votoDto.setOpcao(OpcaoVoto.Nao);
        when(sessaoService.aberta(1)).thenReturn(true);
        when(repository.existsByCodigoSessaoAndCodigoAssociado(1, 1)).thenReturn(false);
        service.votar(1, votoDto);
    }

    @Test
    public void getResultadoTest() {
        when(pautaService.getById(1)).thenReturn(getFakePauta());
        when(repository.countResultados(1)).thenReturn(getFakeListResultado());
        PautaResultadoDto pautaResultado = service.getResultadoVotacao(1);
        assertEquals("Sim", pautaResultado.getResultado().get(0).getOpcao());
        assertEquals("2", pautaResultado.getResultado().get(0).getQtd().toString());
        assertEquals("Nao", pautaResultado.getResultado().get(1).getOpcao());
        assertEquals("3", pautaResultado.getResultado().get(1).getQtd().toString());
    }

    private PautaDto getFakePauta() {
        PautaDto pauta = new PautaDto();
        pauta.setDescricao("DESCRIÇÃO PAUTA");
        pauta.setCodigo(1);
        return pauta;
    }

    private List<VotacaoResultadoDto> getFakeListResultado() {
        return
                Arrays.asList(
                        new VotacaoResultadoDto("Sim", Long.parseLong("2")),
                        new VotacaoResultadoDto("Nao", Long.parseLong("3")));
    }
}

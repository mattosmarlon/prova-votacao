package br.com.prova.provavotacao.service;

import br.com.prova.provavotacao.domain.dto.PautaDto;
import br.com.prova.provavotacao.domain.dto.UsuarioHabilitadoDto;
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
import br.com.prova.provavotacao.infrastructure.facade.UserInfoFacade;
import br.com.prova.provavotacao.infrastructure.service.NotificacaoResultadoService;
import br.com.prova.provavotacao.infrastructure.service.VotacaoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Mock
    private UserInfoFacade UserInfoFacade;

    @Mock
    private NotificacaoResultadoService notificacaoResultadoService;

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

    @Test(expected = NegocioException.class)
    public void votarAssociadoNaoHabilitadoRestTest() {
        UsuarioHabilitadoDto fakeInfo = new UsuarioHabilitadoDto();
        fakeInfo.setStatus("NOT");
        VotoDto votoDto = new VotoDto();
        votoDto.setCodigoAssociado(1);
        votoDto.setOpcao(OpcaoVoto.Nao);
        votoDto.setCpf("00100100102");
        when(sessaoService.aberta(1)).thenReturn(true);
        when(UserInfoFacade.usuarioHabilitado("00100100102")).thenReturn(Optional.of(fakeInfo));
        when(repository.existsByCodigoSessaoAndCodigoAssociado(1, 1)).thenReturn(false);
        service.votar(1, votoDto);
    }

    @Test(expected = NegocioException.class)
    public void votarAssociadoNaoHabilitadoRestNullTest() {
        //TODO: Criar builders para esses objetos iguais
        VotoDto votoDto = new VotoDto();
        votoDto.setCodigoAssociado(1);
        votoDto.setOpcao(OpcaoVoto.Nao);
        votoDto.setCpf("00100100102");
        when(sessaoService.aberta(1)).thenReturn(true);
        when(UserInfoFacade.usuarioHabilitado("00100100102")).thenReturn(Optional.empty());
        when(repository.existsByCodigoSessaoAndCodigoAssociado(1, 1)).thenReturn(false);
        service.votar(1, votoDto);
    }

    @Test
    public void votarSucessoTest() {
        UsuarioHabilitadoDto fakeInfo = new UsuarioHabilitadoDto();
        fakeInfo.setStatus("ABLE_TO_VOTE");
        VotoDto votoDto = new VotoDto();
        votoDto.setCodigoAssociado(1);
        votoDto.setOpcao(OpcaoVoto.Nao);
        votoDto.setCpf("00100100102");
        when(sessaoService.aberta(1)).thenReturn(true);
        when(UserInfoFacade.usuarioHabilitado("00100100102")).thenReturn(Optional.of(fakeInfo));
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

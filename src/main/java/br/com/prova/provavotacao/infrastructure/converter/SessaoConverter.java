package br.com.prova.provavotacao.infrastructure.converter;

import br.com.prova.provavotacao.domain.dto.SessaoDto;
import br.com.prova.provavotacao.domain.dto.create.SessaoCreateDto;
import br.com.prova.provavotacao.domain.entity.Sessao;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@Component
public class SessaoConverter {

    public SessaoDto convert(Sessao sessao) {
        SessaoDto sessaoDto = new SessaoDto();
        sessaoDto.setCodigo(sessao.getCodigo());
        sessaoDto.setCodigoPauta(sessao.getCodigoPauta());
        sessaoDto.setDataAbertura(sessao.getDataAbertura());
        sessaoDto.setDataEncerramento(sessao.getDataEncerramento());
        sessaoDto.setDataCadastro(sessao.getDataCadastro());
        return sessaoDto;
    }

    public Sessao convert(SessaoCreateDto sessaoCreateDto) {
        Sessao sessao = new Sessao();
        sessao.setCodigoPauta(sessaoCreateDto.getCodigoPauta());
        sessao.setDataAbertura(sessaoCreateDto.getDataAbertura());
        sessao.setDataEncerramento(sessaoCreateDto.getDataEncerramento());
        sessao.setDataCadastro(Date.from(Instant.now()));
        return sessao;
    }
}

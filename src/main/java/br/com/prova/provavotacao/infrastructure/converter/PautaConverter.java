package br.com.prova.provavotacao.infrastructure.converter;

import br.com.prova.provavotacao.domain.dto.PautaDto;
import br.com.prova.provavotacao.domain.dto.create.PautaCreateDto;
import br.com.prova.provavotacao.domain.entity.Pauta;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;

@Component
public class PautaConverter {

    public PautaDto convert(Pauta pauta) {
        PautaDto pautaDto = new PautaDto();
        pautaDto.setCodigo(pauta.getCodigo());
        pautaDto.setDescricao(pauta.getDescricao());
        pautaDto.setDataCadastro(pauta.getDataCadastro());
        return pautaDto;
    }

    public Pauta convert(PautaCreateDto pautaCreateDto) {
        Pauta pauta = new Pauta();
        pauta.setDescricao(pautaCreateDto.getDescricao());
        pauta.setDataCadastro(Date.from(Instant.now()));
        return pauta;
    }
}

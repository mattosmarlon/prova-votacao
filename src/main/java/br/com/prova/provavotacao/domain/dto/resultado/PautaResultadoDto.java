package br.com.prova.provavotacao.domain.dto.resultado;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class PautaResultadoDto {

    private String descricao;

    private List<VotacaoResultadoDto> resultado;

    public PautaResultadoDto() {
    }

    public PautaResultadoDto(String descricao, List<VotacaoResultadoDto> resultado) {
        this.descricao = descricao;
        this.resultado = resultado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<VotacaoResultadoDto> getResultado() {
        return resultado;
    }

    public void setResultado(List<VotacaoResultadoDto> resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

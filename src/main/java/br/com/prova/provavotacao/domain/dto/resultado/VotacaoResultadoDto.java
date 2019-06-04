package br.com.prova.provavotacao.domain.dto.resultado;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class VotacaoResultadoDto {

    private String opcao;

    private Long qtd;

    public VotacaoResultadoDto() {
    }

    public VotacaoResultadoDto(String opcao, Long qtd) {
        this.opcao = opcao;
        this.qtd = qtd;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public Long getQtd() {
        return qtd;
    }

    public void setQtd(Long qtd) {
        this.qtd = qtd;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

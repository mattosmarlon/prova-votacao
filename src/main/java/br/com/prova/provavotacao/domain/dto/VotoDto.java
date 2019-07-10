package br.com.prova.provavotacao.domain.dto;

import br.com.prova.provavotacao.domain.dto.enums.OpcaoVoto;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class VotoDto {

    private OpcaoVoto opcao;

    private Integer codigoAssociado;

    private String cpf;

    public OpcaoVoto getOpcao() {
        return opcao;
    }

    public void setOpcao(OpcaoVoto opcao) {
        this.opcao = opcao;
    }

    public Integer getCodigoAssociado() {
        return codigoAssociado;
    }

    public void setCodigoAssociado(Integer codigoAssociado) {
        this.codigoAssociado = codigoAssociado;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

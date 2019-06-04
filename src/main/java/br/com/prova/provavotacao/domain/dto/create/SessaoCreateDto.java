package br.com.prova.provavotacao.domain.dto.create;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class SessaoCreateDto {

    private Integer codigoPauta;

    private Date dataAbertura;

    private Date dataEncerramento;

    public Integer getCodigoPauta() {
        return codigoPauta;
    }

    public void setCodigoPauta(Integer codigoPauta) {
        this.codigoPauta = codigoPauta;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Date getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Date dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

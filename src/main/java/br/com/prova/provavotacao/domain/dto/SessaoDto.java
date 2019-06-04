package br.com.prova.provavotacao.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public class SessaoDto {

    private Integer codigo;

    private Integer codigoPauta;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataAbertura;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataEncerramento;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCadastro;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

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

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

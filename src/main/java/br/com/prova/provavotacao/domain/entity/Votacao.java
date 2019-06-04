package br.com.prova.provavotacao.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "VOTACAO")
public class Votacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO")
    private Integer codigo;

    @Column(name = "CODIGO_SESSAO")
    private Integer codigoSessao;

    @Column(name = "CODIGO_ASSOCIADO")
    private Integer codigoAssociado;

    @Column(name = "OPCAO")
    private String opcao;

    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "CODIGO_SESSAO", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    private Sessao sessao;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigoSessao() {
        return codigoSessao;
    }

    public void setCodigoSessao(Integer codigoSessao) {
        this.codigoSessao = codigoSessao;
    }

    public Integer getCodigoAssociado() {
        return codigoAssociado;
    }

    public void setCodigoAssociado(Integer codigoAssociado) {
        this.codigoAssociado = codigoAssociado;
    }

    public String getOpcao() {
        return opcao;
    }

    public void setOpcao(String opcao) {
        this.opcao = opcao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

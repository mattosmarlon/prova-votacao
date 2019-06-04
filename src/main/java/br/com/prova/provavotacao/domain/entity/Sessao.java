package br.com.prova.provavotacao.domain.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "SESSAO")
public class Sessao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CODIGO")
    private Integer codigo;

    @Column(name = "CODIGO_PAUTA")
    private Integer codigoPauta;

    @Column(name = "DATA_ABERTURA")
    private Date dataAbertura;

    @Column(name = "DATA_ENCERRAMENTO")
    private Date dataEncerramento;

    @Column(name = "DATA_CADASTRO")
    private Date dataCadastro;

    @ManyToOne
    @JoinColumn(name = "CODIGO_PAUTA", referencedColumnName = "CODIGO", insertable = false, updatable = false)
    private Pauta pauta;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sessao")
    private List<Votacao> votacoes;

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

    public Pauta getPauta() {
        return pauta;
    }

    public void setPauta(Pauta pauta) {
        this.pauta = pauta;
    }

    public List<Votacao> getVotacoes() {
        return votacoes;
    }

    public void setVotacoes(List<Votacao> votacoes) {
        this.votacoes = votacoes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

package br.com.prova.provavotacao.domain.repository;

import br.com.prova.provavotacao.domain.dto.resultado.VotacaoResultadoDto;
import br.com.prova.provavotacao.domain.entity.Votacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VotacaoRepository extends CrudRepository<Votacao, Integer> {

    Boolean existsByCodigoSessaoAndCodigoAssociado(Integer codigoSessao, Integer codigoAssociado);

    @Query(value =
            "SELECT " +
                    "    new br.com.prova.votacao.domain.dto.resultado.VotacaoResultadoDto(V.opcao, count(*)) " +
                    "FROM Pauta P " +
                    "JOIN Sessao S " +
                    "ON S.codigoPauta = P.codigo " +
                    "JOIN Votacao V " +
                    "ON V.codigoSessao = S.codigo " +
                    "WHERE " +
                    "    P.codigo = :pautaId " +
                    "GROUP BY " +
                    "    V.opcao ")
    List<VotacaoResultadoDto> countResultados(@Param("pautaId") Integer pautaId);
}

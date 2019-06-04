package br.com.prova.provavotacao.api.v1;

import br.com.prova.provavotacao.domain.dto.VotoDto;
import br.com.prova.provavotacao.domain.dto.exception.ErroExceptionDto;
import br.com.prova.provavotacao.domain.dto.resultado.PautaResultadoDto;
import br.com.prova.provavotacao.domain.service.VotacaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/voto")
public class VotoApi {

    @Autowired
    private VotacaoService votacaoService;

    @GetMapping("/{pautaId}/resultado")

    @ApiOperation(
            value = "Consulta os resultados da pauta de votação.",
            notes = "Exibe os resultados de uma pauta de votação."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = PautaResultadoDto.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity<PautaResultadoDto> get(@PathVariable Integer pautaId) {
        return ResponseEntity.ok(votacaoService.getResultadoVotacao(pautaId));
    }


    @PostMapping("/{sessaoId}")
    @ApiOperation(
            value = "Executa o voto de um Associado (Sim ou Nao).",
            notes = "Envia o voto de um associado."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity votar(@PathVariable Integer sessaoId, @RequestBody VotoDto votoDto) {
        votacaoService.votar(sessaoId, votoDto);
        return ResponseEntity.ok().build();
    }
}

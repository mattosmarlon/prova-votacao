package br.com.prova.provavotacao.api.v1;

import br.com.prova.provavotacao.domain.dto.PautaDto;
import br.com.prova.provavotacao.domain.dto.create.PautaCreateDto;
import br.com.prova.provavotacao.domain.dto.exception.ErroExceptionDto;
import br.com.prova.provavotacao.domain.service.PautaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pauta")
public class PautaApi {

    @Autowired
    private PautaService pautaService;

    @GetMapping("/{id}")
    @ApiOperation(
            value = "Consulta as principais informações de uma pauta.",
            notes = "Exibe apenas os dados da Pauta."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = PautaDto.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity<PautaDto> get(@PathVariable Integer id) {
        return ResponseEntity.ok(pautaService.getById(id));
    }

    @PostMapping
    @ApiOperation(
            value = "Cria uma nova pauta.",
            notes = "Possibilita a criação de uma sessão de votação."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = PautaDto.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity<PautaDto> create(@RequestBody PautaCreateDto pautaCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pautaService.create(pautaCreateDto));
    }

    @DeleteMapping("/{id}")
    @PostMapping
    @ApiOperation(
            value = "Exclui uma Pauta existente.",
            notes = "Remove uma Pauta que não possui nenhuma sessão."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity delete(@PathVariable Integer id) {
        pautaService.delete(id);
        return ResponseEntity.ok().build();
    }
}

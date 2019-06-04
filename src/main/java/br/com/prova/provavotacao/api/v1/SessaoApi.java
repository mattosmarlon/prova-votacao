package br.com.prova.provavotacao.api.v1;

import br.com.prova.provavotacao.domain.dto.SessaoDto;
import br.com.prova.provavotacao.domain.dto.exception.ErroExceptionDto;
import br.com.prova.provavotacao.domain.service.SessaoService;
import br.com.prova.provavotacao.domain.dto.create.SessaoCreateDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessao")
public class SessaoApi {

    @Autowired
    private SessaoService sessaoService;

    @GetMapping("/{id}")
    @ApiOperation(
            value = "Consulta uma sessão de votação.",
            notes = "Exibe a vigência de uma sessão de votação."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = SessaoDto.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity<SessaoDto> get(@PathVariable Integer id) {
        return ResponseEntity.ok(sessaoService.getById(id));
    }

    @GetMapping("/{id}/aberta")
    @ApiOperation(
            value = "Verifica a vigência de uma sessão.",
            notes = "Verifica a validade da sessão."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Boolean.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity<Boolean> aberta(@PathVariable Integer id) {
        return ResponseEntity.ok(sessaoService.aberta(id));
    }

    @PostMapping
    @ApiOperation(
            value = "Cria uma nova sessão de votação.",
            notes = "Cria uma sessão de votação."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = SessaoDto.class),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity<SessaoDto> create(@RequestBody SessaoCreateDto sessaoCreateDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sessaoService.create(sessaoCreateDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(
            value = "Exclui uma sessão de votação.",
            notes = "Remove uma sessão de votação."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "NOT FOUND", response = ErroExceptionDto.class),
            @ApiResponse(code = 400, message = "BAD REQUEST", response = ErroExceptionDto.class),
    })
    public ResponseEntity delete(@PathVariable Integer id) {
        sessaoService.delete(id);
        return ResponseEntity.ok().build();
    }
}

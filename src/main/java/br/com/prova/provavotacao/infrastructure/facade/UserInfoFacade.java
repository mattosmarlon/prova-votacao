package br.com.prova.provavotacao.infrastructure.facade;

import br.com.prova.provavotacao.domain.dto.UsuarioHabilitadoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "user-info", url = "https://user-info.herokuapp.com/", decode404 = true)
public interface UserInfoFacade {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{cpf}")
    Optional<UsuarioHabilitadoDto> usuarioHabilitado(@PathVariable("cpf") String cpf);
}

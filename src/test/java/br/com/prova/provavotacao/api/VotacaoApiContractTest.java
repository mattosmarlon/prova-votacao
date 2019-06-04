package br.com.prova.provavotacao.api;

import br.com.prova.provavotacao.Application;
import br.com.prova.provavotacao.domain.dto.VotoDto;
import br.com.prova.provavotacao.domain.service.VotacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class VotacaoApiContractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VotacaoService service;

    @Test
    public void votarTest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new VotoDto());

        mvc.perform(post("/api/v1/voto/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void getResultadoTest() throws Exception {
        //String json = new ObjectMapper().writeValueAsString(new VotoDto());

        mvc.perform(get("/api/v1/voto/1/resultado"))
                .andExpect(status().isOk());
    }
}

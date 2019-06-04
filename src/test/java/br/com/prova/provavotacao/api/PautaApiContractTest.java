package br.com.prova.provavotacao.api;

import br.com.prova.provavotacao.Application;
import br.com.prova.provavotacao.domain.dto.PautaDto;
import br.com.prova.provavotacao.domain.dto.create.PautaCreateDto;
import br.com.prova.provavotacao.domain.service.PautaService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=test", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
public class PautaApiContractTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PautaService service;

    @Test
    public void getTest() throws Exception {
        when(service.getById(1)).thenReturn(new PautaDto());
        mvc.perform(get("/api/v1/pauta/1")).andExpect(status().isOk());
    }

    @Test
    public void createTest() throws Exception {
        when(service.create(new PautaCreateDto())).thenReturn(new PautaDto());

        String json = new ObjectMapper().writeValueAsString(new PautaCreateDto());

        mvc.perform(post("/api/v1/pauta")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteTest() throws Exception {
        mvc.perform(delete("/api/v1/pauta/1")).andExpect(status().isOk());
    }
}

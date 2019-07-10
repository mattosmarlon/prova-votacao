package br.com.prova.provavotacao.infrastructure.service;

import br.com.prova.provavotacao.domain.dto.resultado.PautaResultadoDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoResultadoService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Value("${notificacao.exchange}")
    private String exchange;

    @Value("${notificacao.routing-Key}")
    private String routingKey;

    @Autowired
    private ObjectMapper mapper;

    public void notificar(PautaResultadoDto pautaResultadoDto) {
        logger.info("Notificando resultado da votacao: {}", pautaResultadoDto);
        amqpTemplate.convertAndSend(exchange, routingKey, toJsonMessage(pautaResultadoDto));
        logger.info("Notificando executada com sucesso.");
    }

    private String toJsonMessage(PautaResultadoDto pautaResultadoDto){
        String content;
        try {
            content = mapper.writeValueAsString(pautaResultadoDto);
        } catch (JsonProcessingException | NullPointerException e) {
            content = null;
        }
        return content;
    }
}

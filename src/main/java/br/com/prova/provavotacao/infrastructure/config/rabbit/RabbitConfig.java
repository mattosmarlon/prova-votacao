package br.com.prova.provavotacao.infrastructure.config.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Value;

/*
DIFICULDADE ENCONTRADA: Para verificar visualmente a funcionadalidae de postar em uma fila
eu queria fazer com que fosse possivel abrir o portal Admin do Rabbit e verificar a fila com a mensagem,
a questão enfrentada é que seria necessario criar as filas anteiormente, pois, apenas postar na
fila não cria a exchange completa de acordo com a routingKey.
Assim, esta classe foi criada para criar o exchange vinculado a fila, porém, durantes a validação dos teste
onde sobe o contexto da aplicação não era encontrado o rabbit online.
Para solucionar isso de forma rapida, as filas e o exchange foram criadas juntamente com o container no docker-compose.
 */
//@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port:5672}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    //    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

    //    @Bean
    public AmqpAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    //    @Bean
    public TopicExchange resultadoExchange() {
        return new TopicExchange("exchange.resultado", true, false);
    }

    //    @Bean
    public Queue resultadoUmQeue() {
        return new Queue("queue.resultado.1", true, false, false);
    }

    //    @Bean
    public Queue resultadoDoisQueue() {
        return new Queue("queue.resultado.2", true, false, false);
    }

    //    @Bean
    public Binding resultadoExchangeBindingUm() {
        return BindingBuilder.bind(resultadoUmQeue()).to(resultadoExchange()).with("#");
    }

    //    @Bean
    public Binding resultadoExchangeBindingDois() {
        return BindingBuilder.bind(resultadoDoisQueue()).to(resultadoExchange()).with("#");
    }
}

package ba.unsa.etf.nwt.pet_category_service.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String PET_ADOPT_SERVICE_QUEUE = "pet_category_adopt_service_queue";
    public static final String PET_ADOPT_SERVICE_EXCHANGE = "pet_category_adopt_service_exchange";
    public static final String PET_ADOPT_SERVICE_ROUTING_KEY = "pet_category_adopt_service_routing_key";
    
    @Bean
    public Queue queue(){
        return new Queue(PET_ADOPT_SERVICE_QUEUE);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(PET_ADOPT_SERVICE_EXCHANGE);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(PET_ADOPT_SERVICE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}

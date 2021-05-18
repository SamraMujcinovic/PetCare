package ba.unsa.etf.nwt.notification_service.rabbitmq;

import ba.unsa.etf.nwt.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    @Autowired
    private NotificationService notificationService;

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(queues = "user_notification_service_queue")
    public void consumeMessageFromQueue(NotificationServiceMessage notificationServiceMessage){
        notificationService.contactUsAndRegistrationNotification(notificationServiceMessage.getUserId(), notificationServiceMessage.getContent());
    }
}

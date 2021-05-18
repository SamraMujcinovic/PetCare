package ba.unsa.etf.nwt.notification_service.rabbitmq.consumer;

import ba.unsa.etf.nwt.notification_service.rabbitmq.MessagingConfig;
import ba.unsa.etf.nwt.notification_service.rabbitmq.consumer.NotificationServiceMessage;
import ba.unsa.etf.nwt.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "user_notification_service_queue")
    public void consumeMessageFromQueue(NotificationServiceMessage notificationServiceMessage){
        try {
            notificationService.contactUsAndRegistrationNotification(notificationServiceMessage.getUserId(), notificationServiceMessage.getContent());
        } catch (Exception e){
            //send back message to user_service
            rabbitTemplate.convertAndSend(MessagingConfig.NOTIFICATION_SERVICE_EXCHANGE,
                    MessagingConfig.NOTIFICATION_SERVICE_ROUTING_KEY, notificationServiceMessage);
        }
    }
}

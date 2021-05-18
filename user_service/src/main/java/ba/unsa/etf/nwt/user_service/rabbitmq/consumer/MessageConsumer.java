package ba.unsa.etf.nwt.user_service.rabbitmq.consumer;

import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.model.User;
import ba.unsa.etf.nwt.user_service.rabbitmq.NotificationServiceMessage;
import ba.unsa.etf.nwt.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageConsumer {

    @Autowired
    private UserService userService;

    @RabbitListener(queues = "notification_service_queue")
    public void consumeMessageFromQueue(NotificationServiceMessage notificationServiceMessage){
        User user = userService.findById(notificationServiceMessage.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        userService.delete(user);
    }
}

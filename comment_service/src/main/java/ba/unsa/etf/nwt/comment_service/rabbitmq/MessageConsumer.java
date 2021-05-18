package ba.unsa.etf.nwt.comment_service.rabbitmq;

import ba.unsa.etf.nwt.comment_service.service.CommentService;
import ba.unsa.etf.nwt.comment_service.service.ReplyService;
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
    private CommentService commentService;

    @Autowired
    private ReplyService replyService;

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @RabbitListener(queues = "comment_service_queue")
    public void consumeMessageFromQueue(CommentServiceMessage commentServiceMessage){
        System.out.println("Message from RabbitMQ: " + commentServiceMessage.getMessage());
        commentService.setUsernameOnUnknown(commentServiceMessage.getUsername());
        replyService.setUsernameOnUnknown(commentServiceMessage.getUsername());
    }
}

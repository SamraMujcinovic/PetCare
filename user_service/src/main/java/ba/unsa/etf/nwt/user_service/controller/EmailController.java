package ba.unsa.etf.nwt.user_service.controller;

import ba.unsa.etf.nwt.user_service.email.EmailCfg;
import ba.unsa.etf.nwt.user_service.email.FeedBack;
import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.exception.WrongInputException;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailCfg emailCfg;

    public EmailController(EmailCfg emailCfg) {
        this.emailCfg = emailCfg;
    }

    @PostMapping("/send")
    public ResponseMessage sendEmail(@RequestBody FeedBack feedback, BindingResult bindingResult){

        try {
            if (bindingResult.hasErrors()) {
                throw new WrongInputException("Info for the email is not valid!");
            }

            //Create mail sender
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(this.emailCfg.getHost());
            mailSender.setPort(this.emailCfg.getPort());
            mailSender.setUsername(this.emailCfg.getUsername());
            mailSender.setPassword(this.emailCfg.getPassword());

            //Set properties for gmail server
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            //Create an email instance
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(feedback.getEmail());
            mailMessage.setTo("nwt.pet.care.adm2021@gmail.com");
            mailMessage.setSubject("New contact us form filled by " + feedback.getName());
            mailMessage.setText(feedback.getFeedback());

            //Send mail
            mailSender.send(mailMessage);

            return new ResponseMessage(true, HttpStatus.OK, "You have successfully sent an email!");
        }
        catch (Exception e){
            throw new ResourceNotFoundException("Can't connect to email server!");
        }
    }

}

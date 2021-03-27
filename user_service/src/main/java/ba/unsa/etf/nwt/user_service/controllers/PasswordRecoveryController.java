package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordAnswerRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordRecoveryRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordQuestionRequest;
import ba.unsa.etf.nwt.user_service.responses.QuestionResponse;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recovery")
public class PasswordRecoveryController {
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/securityquestion")
    public QuestionResponse getSecurityQuestion(@RequestBody PasswordQuestionRequest passwordQuestionRequest){
        return passwordService.getQuestion(passwordQuestionRequest);
    }

    @PostMapping("/answerQuestion")
    public ResponseMessage answerQuestion(@RequestBody PasswordAnswerRequest passwordAnswerRequest) {
        return passwordService.getAnswerOfQuestion(passwordAnswerRequest);
    }

    @PostMapping("/newPassword")
    public ResponseMessage getNewPassword(@RequestBody PasswordRecoveryRequest passwordRecoveryRequest) {
        return passwordService.recoverPassword(passwordRecoveryRequest);
    }
}

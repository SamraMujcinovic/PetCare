package ba.unsa.etf.nwt.user_service.controller;

import ba.unsa.etf.nwt.user_service.request.password_requests.PasswordAnswerRequest;
import ba.unsa.etf.nwt.user_service.request.password_requests.PasswordChangeRequest;
import ba.unsa.etf.nwt.user_service.request.password_requests.PasswordQuestionRequest;
import ba.unsa.etf.nwt.user_service.response.QuestionResponse;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import ba.unsa.etf.nwt.user_service.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/change")
public class PasswordChangeController {
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
    public ResponseMessage getNewPassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        return passwordService.changePassword(passwordChangeRequest);
    }
}

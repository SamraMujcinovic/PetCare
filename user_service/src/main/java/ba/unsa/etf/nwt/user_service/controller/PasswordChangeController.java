package ba.unsa.etf.nwt.user_service.controller;

import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.exception.WrongInputException;
import ba.unsa.etf.nwt.user_service.model.User;
import ba.unsa.etf.nwt.user_service.request.password_requests.PasswordAnswerRequest;
import ba.unsa.etf.nwt.user_service.request.password_requests.PasswordChangeRequest;
import ba.unsa.etf.nwt.user_service.request.password_requests.PasswordQuestionRequest;
import ba.unsa.etf.nwt.user_service.response.QuestionResponse;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import ba.unsa.etf.nwt.user_service.service.PasswordService;
import ba.unsa.etf.nwt.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/change")
public class PasswordChangeController {
    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    @PostMapping("/securityquestion")
    public QuestionResponse getSecurityQuestion(@Valid @RequestBody PasswordQuestionRequest passwordQuestionRequest){
        return passwordService.getQuestion(passwordQuestionRequest);
    }

    @PostMapping("/answerQuestion")
    public ResponseMessage answerQuestion(@Valid @RequestBody PasswordAnswerRequest passwordAnswerRequest) {
        return passwordService.getAnswerOfQuestion(passwordAnswerRequest);
    }

    @PostMapping("/newPassword")
    public ResponseMessage getNewPassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        User user = userService.findByEmail(passwordChangeRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (passwordChangeRequest.getAnswer().getText().equals(user.getAnswer().getText())) {

            if (!passwordChangeRequest.getOldPassword().equals(user.getPassword())) {
                throw new WrongInputException("Old password is not a match!");
            }

            user.setPassword(passwordChangeRequest.getNewPassword());
            userService.save(user);
            return new ResponseMessage(true, HttpStatus.OK, "You have successfully changed your password.");
        } else {
            throw new WrongInputException("Wrong answer!");
        }
    }
}

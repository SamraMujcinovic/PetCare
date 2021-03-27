package ba.unsa.etf.nwt.user_service.services;

import ba.unsa.etf.nwt.user_service.models.Question;
import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordAnswerRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordChangeRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordQuestionRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordRecoveryRequest;
import ba.unsa.etf.nwt.user_service.responses.QuestionResponse;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    @Autowired
    private UserService userService;

    @Autowired
    private ValidationsService validationsService;

    public QuestionResponse getQuestion(PasswordQuestionRequest passwordQuestionRequest){

        QuestionResponse rm = validationsService.validatePasswordQuestion(passwordQuestionRequest);
        if(!rm.getSuccess()){
            return new QuestionResponse(false, rm.getMessage(), rm.getStatus(), new Question());
        }

        try {
            User user = userService.findByEmail(passwordQuestionRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user == null)
                throw new RuntimeException("User does not exist");

            return new QuestionResponse(true, "Valid email, question found!!",
                    "OK", user.getAnswer().getQuestion());
        }
        catch (RuntimeException e){
            return new QuestionResponse(false, e.getMessage(), "NOT_FOUND", new Question());
        }
    }

    public ResponseMessage getAnswerOfQuestion(PasswordAnswerRequest passwordAnswerRequest){

        ResponseMessage rm = validationsService.validatePasswordAnswer(passwordAnswerRequest);
        if(!rm.getSuccess()){
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        try {
            User user = userService.findByEmail(passwordAnswerRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user == null)
                throw new RuntimeException("User does not exist");

            if(passwordAnswerRequest.getAnswer().equals(user.getAnswer().getText())){
                return new ResponseMessage(true, "You have successfully answered the question",
                        "OK");
            }

            return new ResponseMessage(false, "Wrong answer!!", "NOT_FOUND");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, e.getMessage(), "NOT_FOUND");
        }
    }

    public ResponseMessage recoverPassword(PasswordRecoveryRequest passwordRecoveryRequest){
        ResponseMessage rm = validationsService.validatePasswordRecoveryChange(passwordRecoveryRequest.getEmail(),
                passwordRecoveryRequest.getAnswer(), passwordRecoveryRequest.getNewPassword());
        if(!rm.getSuccess()){
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        try {
            User user = userService.findByEmail(passwordRecoveryRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user == null)
                throw new RuntimeException("User does not exist");

            if(passwordRecoveryRequest.getAnswer().equals(user.getAnswer().getText())){
                user.setPassword(passwordRecoveryRequest.getNewPassword());
                userService.save(user);
                return new ResponseMessage(true, "You have successfully recovered your password",
                        "OK");
            }

            return new ResponseMessage(false, "Wrong answer!!", "NOT_FOUND");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, e.getMessage(), "NOT_FOUND");
        }
    }

    public ResponseMessage changePassword(PasswordChangeRequest passwordChangeRequest){
        ResponseMessage rm = validationsService.validatePasswordRecoveryChange(passwordChangeRequest.getEmail(),
                passwordChangeRequest.getAnswer(), passwordChangeRequest.getNewPassword());
        if(!rm.getSuccess()){
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        try {
            User user = userService.findByEmail(passwordChangeRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user == null)
                throw new RuntimeException("User does not exist");

            if(passwordChangeRequest.getAnswer().equals(user.getAnswer().getText())){

                if(!passwordChangeRequest.getOldPassword().equals(user.getPassword())){
                    return new ResponseMessage(false, "Old password is not a match!!", "NOT_FOUND");
                }

                user.setPassword(passwordChangeRequest.getNewPassword());
                userService.save(user);
                return new ResponseMessage(true, "You have successfully changed your password",
                        "OK");
            }

            return new ResponseMessage(false, "Wrong answer!!", "NOT_FOUND");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, e.getMessage(), "NOT_FOUND");
        }
    }

}

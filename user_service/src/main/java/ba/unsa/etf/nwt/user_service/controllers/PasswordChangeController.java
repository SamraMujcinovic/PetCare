package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.Question;
import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordAnswerRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordQuestionRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordRecoveryRequest;
import ba.unsa.etf.nwt.user_service.responses.QuestionResponse;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/change")
public class PasswordChangeController {
    @Autowired
    private UserService userService;

    @PostMapping("/securityquestion")
    public QuestionResponse getSecurityQuestion(@RequestBody PasswordQuestionRequest passwordQuestionRequest){

        return new QuestionResponse(false, "...", "ERROR", new Question());

        //isto radi samo sa CurrentUserom, nakon autentifikacije...
        //kao parametar se stavlja current user itd...

        /*if(passwordQuestionRequest.getEmail().isEmpty() || !passwordQuestionRequest.getEmail().contains("@")){
            return new QuestionResponse(false, "Email is not valid!!",
                    "BAD_REQUEST", new Question());
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
            return new QuestionResponse(false, e.getMessage(), "ERROR", new Question());
        }*/
    }

    @PostMapping("/answerQuestion")
    public ResponseMessage answerQuestion(@RequestBody PasswordAnswerRequest passwordAnswerRequest) {

        return new ResponseMessage(false, "...", "ERROR");

        //isto samo sa CuurentUserom

        /*if(passwordAnswerRequest.getEmail().isEmpty() || !passwordAnswerRequest.getEmail().contains("@")){
            return new ResponseMessage(false, "Email is not valid!!",
                    "BAD_REQUEST");
        }

        if(passwordAnswerRequest.getAnswer().isEmpty()){
            return new ResponseMessage(false, "Answer can't be blank!!",
                    "BAD_REQUEST");
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

            return new ResponseMessage(false, "Wrong answer!!", "ERROR");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, e.getMessage(), "ERROR");
        }*/
    }

    @PostMapping("/newPassword")
    public ResponseMessage getNewPassword(@RequestBody PasswordRecoveryRequest passwordRecoveryRequest) {

        return new ResponseMessage(false, "...", "ERROR");

        //isto samo sa CurrentUserom i PasswordChangeRequest

        /*if(passwordRecoveryRequest.getEmail().isEmpty() || !passwordRecoveryRequest.getEmail().contains("@")){
            return new ResponseMessage(false, "Email is not valid!!",
                    "BAD_REQUEST");
        }

        if(passwordRecoveryRequest.getAnswer().isEmpty()){
            return new ResponseMessage(false, "Answer can't be blank!!",
                    "BAD_REQUEST");
        }

        if(passwordRecoveryRequest.getNewPassword().length() < 6 ||
                passwordRecoveryRequest.getNewPassword().length() > 40){
            return new ResponseMessage(false,
                    "New password not valid (must have at least 6 characters)!!",
                    "BAD_REQUEST");
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

            return new ResponseMessage(false, "Wrong answer!!", "ERROR");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, e.getMessage(), "ERROR");
        }*/
    }

}

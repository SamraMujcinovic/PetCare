package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.Question;
import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordAnswerRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordChangeRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordQuestionRequest;
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

        if(passwordQuestionRequest.getEmail().isEmpty() || !passwordQuestionRequest.getEmail().contains("@")){
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
            return new QuestionResponse(false, e.getMessage(), "NOT_FOUND", new Question());
        }
    }

    @PostMapping("/answerQuestion")
    public ResponseMessage answerQuestion(@RequestBody PasswordAnswerRequest passwordAnswerRequest) {

        if(passwordAnswerRequest.getEmail().isEmpty() || !passwordAnswerRequest.getEmail().contains("@")){
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

            return new ResponseMessage(false, "Wrong answer!!", "NOT_FOUND");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, e.getMessage(), "NOT_FOUND");
        }
    }

    @PostMapping("/newPassword")
    public ResponseMessage getNewPassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {

        if(passwordChangeRequest.getEmail().isEmpty() || !passwordChangeRequest.getEmail().contains("@")){
            return new ResponseMessage(false, "Email is not valid!!",
                    "BAD_REQUEST");
        }

        if(passwordChangeRequest.getAnswer().isEmpty()){
            return new ResponseMessage(false, "Answer can't be blank!!",
                    "BAD_REQUEST");
        }

        if(passwordChangeRequest.getNewPassword().length() < 6 ||
                passwordChangeRequest.getNewPassword().length() > 40){
            return new ResponseMessage(false,
                    "New password not valid (must have at least 6 characters)!!",
                    "BAD_REQUEST");
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

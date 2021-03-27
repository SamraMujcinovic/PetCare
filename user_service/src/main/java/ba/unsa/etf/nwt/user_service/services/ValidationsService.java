package ba.unsa.etf.nwt.user_service.services;

import ba.unsa.etf.nwt.user_service.models.Question;
import ba.unsa.etf.nwt.user_service.requests.*;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordAnswerRequest;
import ba.unsa.etf.nwt.user_service.requests.password_requests.PasswordQuestionRequest;
import ba.unsa.etf.nwt.user_service.responses.QuestionResponse;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationsService {
    @Autowired
    private UserService userService;

    private ResponseMessage validateNameSurname(String name){
        if(name.length() < 2 || name.length() > 50){
            return new ResponseMessage(false, " not valid (at least 2 characters)!!",
                    "BAD_REQUEST");
        }
        return new ResponseMessage(true, "", "");
    }

    private ResponseMessage validateEmail(String email){
        if(email.isEmpty() || !email.contains("@") || email.length() > 100){
            return new ResponseMessage(false, "Email is not valid!!",
                    "BAD_REQUEST");
        }
        return new ResponseMessage(true, "", "");
    }

    private ResponseMessage validateUsername(String username){
        if(username.length() < 4 || username.length() > 40){
            return new ResponseMessage(false, "Username not valid (at least 4 characters)!!",
                    "BAD_REQUEST");
        }
        return new ResponseMessage(true, "", "");
    }

    private ResponseMessage validatePassword(String password){
        if(password.length() < 6 || password.length() > 40){
            return new ResponseMessage(false, "Password not valid (at least 6 characters)!!",
                    "BAD_REQUEST");
        }
        return new ResponseMessage(true, "", "");
    }

    private ResponseMessage validateUsernameOrEmail(String usernameOrEmail){
        if(usernameOrEmail.isEmpty() || usernameOrEmail.length() > 100){
            return new ResponseMessage(false, "Username/email not valid!!",
                    "BAD_REQUEST");
        }
        return new ResponseMessage(true, "", "");
    }

    public ResponseMessage validateQuestionRequest(Question question){
        if(question.getTitle().isEmpty() && question.getDescription().isEmpty()) return new ResponseMessage(false, "Title and description can't be blank!!", "BAD_REQUEST");

        if(question.getTitle().length() > 100) return new ResponseMessage(false, "Title can't have more than 100 characters!!", "BAD_REQUEST");

        if(question.getTitle().isEmpty()) return new ResponseMessage(false, "Title can't be blank!!", "BAD_REQUEST");

        if(question.getDescription().isEmpty()) return new ResponseMessage(false, "Description can't be blank!!", "BAD_REQUEST");

        return new ResponseMessage(true, "", "");
    }

    public ResponseMessage validateRegistration(RegistrationRequest registrationRequest){

        if(!validateNameSurname(registrationRequest.getName()).getSuccess()){
            ResponseMessage rm = validateNameSurname(registrationRequest.getName());
            return new ResponseMessage(false, "Name" + rm.getMessage(), rm.getStatus());
        }

        if(!validateNameSurname(registrationRequest.getSurname()).getSuccess()){
            ResponseMessage rm = validateNameSurname(registrationRequest.getSurname());
            return new ResponseMessage(false, "Surname" + rm.getMessage(), rm.getStatus());
        }

        if(!validateEmail(registrationRequest.getEmail()).getSuccess()){
            ResponseMessage rm = validateEmail(registrationRequest.getEmail());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        if(!validateUsername(registrationRequest.getUsername()).getSuccess()){
            ResponseMessage rm = validateUsername(registrationRequest.getUsername());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        if(!validatePassword(registrationRequest.getPassword()).getSuccess()){
            ResponseMessage rm = validatePassword(registrationRequest.getPassword());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        if(userService.existsByUsername(registrationRequest.getUsername())) {
            return new ResponseMessage(false, "This username is already taken!!",
                    "BAD_REQUEST");
        }

        if(userService.existsByEmail(registrationRequest.getEmail())) {
            return new ResponseMessage(false, "Someone else is already using this email address!!",
                    "BAD_REQUEST");
        }

        if(registrationRequest.getAnswer() == null || registrationRequest.getAnswer().getText().isEmpty()){
            return new ResponseMessage(false, "Answer can't be blank!!",
                    "BAD_REQUEST");
        }

        return new ResponseMessage(true, "", "");
    }

    public ResponseMessage validateLogin(LoginRequest loginRequest){

        if(!validateUsernameOrEmail(loginRequest.getUsernameOrEmail()).getSuccess()){
            ResponseMessage rm = validateUsernameOrEmail(loginRequest.getUsernameOrEmail());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        if(!validatePassword(loginRequest.getPassword()).getSuccess()){
            ResponseMessage rm = validatePassword(loginRequest.getPassword());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        return new ResponseMessage(true, "", "");
    }

    public ResponseMessage validateUserProfile(UserProfileRequest userProfileRequest){
        if(!validateNameSurname(userProfileRequest.getName()).getSuccess()){
            ResponseMessage rm = validateNameSurname(userProfileRequest.getName());
            return new ResponseMessage(false, "Name" + rm.getMessage(), rm.getStatus());
        }

        if(!validateNameSurname(userProfileRequest.getSurname()).getSuccess()){
            ResponseMessage rm = validateNameSurname(userProfileRequest.getSurname());
            return new ResponseMessage(false, "Surname" + rm.getMessage(), rm.getStatus());
        }

        if(!validateEmail(userProfileRequest.getEmail()).getSuccess()){
            ResponseMessage rm = validateEmail(userProfileRequest.getEmail());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        if(!validateUsername(userProfileRequest.getUsername()).getSuccess()){
            ResponseMessage rm = validateUsername(userProfileRequest.getUsername());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        return new ResponseMessage(true, "", "");
    }

    public ResponseMessage validateUserProfile2(UserRequest userRequest){
        if(!validateEmail(userRequest.getEmail()).getSuccess()){
            ResponseMessage rm = validateEmail(userRequest.getEmail());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        if(!validatePassword(userRequest.getPassword()).getSuccess()){
            ResponseMessage rm = validatePassword(userRequest.getPassword());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        return new ResponseMessage(true, "", "");
    }

    public QuestionResponse validatePasswordQuestion(PasswordQuestionRequest passwordQuestionRequest){

        if(!validateEmail(passwordQuestionRequest.getEmail()).getSuccess()){
            ResponseMessage rm = validateEmail(passwordQuestionRequest.getEmail());
            return new QuestionResponse(false, rm.getMessage(), rm.getStatus(), new Question());
        }

        return new QuestionResponse(true, "", "", new Question());
    }

    public ResponseMessage validatePasswordAnswer(PasswordAnswerRequest passwordAnswerRequest){

        if(!validateEmail(passwordAnswerRequest.getEmail()).getSuccess()){
            ResponseMessage rm = validateEmail(passwordAnswerRequest.getEmail());
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        if(passwordAnswerRequest.getAnswer().isEmpty()){
            return new ResponseMessage(false, "Answer can't be blank!!",
                    "BAD_REQUEST");
        }

        return new ResponseMessage(true, "", "");
    }

    public ResponseMessage validatePasswordRecoveryChange(String email, String answer, String password){

        if(!validateEmail(email).getSuccess()){
            ResponseMessage rm = validateEmail(email);
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        if(answer.isEmpty()){
            return new ResponseMessage(false, "Answer can't be blank!!",
                    "BAD_REQUEST");
        }

        if(!validatePassword(password).getSuccess()){
            ResponseMessage rm = validatePassword(password);
            return new ResponseMessage(false, "New " + rm.getMessage(), rm.getStatus());
        }

        return new ResponseMessage(true, "", "");
    }
}

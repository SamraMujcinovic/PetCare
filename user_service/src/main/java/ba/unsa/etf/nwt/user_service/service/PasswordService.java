package ba.unsa.etf.nwt.user_service.service;

import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.exception.WrongInputException;
import ba.unsa.etf.nwt.user_service.model.User;
import ba.unsa.etf.nwt.user_service.request.password_requests.PasswordAnswerRequest;
import ba.unsa.etf.nwt.user_service.request.password_requests.PasswordQuestionRequest;
import ba.unsa.etf.nwt.user_service.response.QuestionResponse;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    @Autowired
    private UserService userService;

    @Autowired
    private GRPCService grpcService;

    public QuestionResponse getQuestion(PasswordQuestionRequest passwordQuestionRequest){
        try {
            User user = userService.findByEmail(passwordQuestionRequest.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

            grpcService.save("POST", "Users", "OK");

            return new QuestionResponse(new ResponseMessage(true, HttpStatus.OK, "Valid email, question found."), user.getAnswer().getQuestion());
        }
        catch (ResourceNotFoundException e){
            grpcService.save("POST", "Users", "ERROR - ResourceNotFound");
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    public ResponseMessage getAnswerOfQuestion(PasswordAnswerRequest passwordAnswerRequest){
        try {
            User user = userService.findByEmail(passwordAnswerRequest.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

            if (passwordAnswerRequest.getAnswer().getText().equals(user.getAnswer().getText())) {
                grpcService.save("POST", "Users", "OK");
                return new ResponseMessage(true, HttpStatus.OK, "You have successfully answered the question.");
            } else {
                grpcService.save("POST", "Users", "ERROR - WrongInput");
                throw new WrongInputException("Wrong answer!");
            }
        }
        catch (ResourceNotFoundException e){
            grpcService.save("POST", "Users", "ERROR - ResourceNotFound");
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}

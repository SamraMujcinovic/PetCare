package ba.unsa.etf.nwt.user_service.controller;

import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.exception.WrongInputException;
import ba.unsa.etf.nwt.user_service.model.Question;
import ba.unsa.etf.nwt.user_service.model.User;
import ba.unsa.etf.nwt.user_service.model.roles.Role;
import ba.unsa.etf.nwt.user_service.model.roles.RoleName;
import ba.unsa.etf.nwt.user_service.request.LoginRequest;
import ba.unsa.etf.nwt.user_service.request.RegistrationRequest;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import ba.unsa.etf.nwt.user_service.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private GRPCService grpcService;

    //private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/{questionId}")
    public ResponseMessage registration(@PathVariable Long questionId, @Valid @RequestBody RegistrationRequest registrationRequest) {

        if(userService.existsByUsername(registrationRequest.getUsername())) {
            grpcService.save("POST", "Users", "ERROR - WrongInput");
            throw new WrongInputException("Username is already taken!");
        }

        if(userService.existsByEmail(registrationRequest.getEmail())) {
            grpcService.save("POST", "Users", "ERROR - WrongInput");
            throw new WrongInputException("Email Address already in use!");
        }

        if(registrationRequest.getAnswer()==null || registrationRequest.getAnswer().getText().isEmpty()){
            grpcService.save("POST", "Users", "ERROR - WrongInput");
            throw new WrongInputException("Answer text must not be empty!");
        }

        try {
            User user = new User(registrationRequest.getName(), registrationRequest.getSurname(),
                    registrationRequest.getEmail(), registrationRequest.getUsername(),
                    registrationRequest.getPassword(), registrationRequest.getAnswer());

            //user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role userRole = roleService.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Roles are not set!!"));

            user.setRoles(Collections.singleton(userRole));

            if (!questionService.existsById(questionId)) {
                grpcService.save("POST", "Users", "ERROR - ResourceNotFound");
                throw new ResourceNotFoundException("Question with given id does not exist!");
            }

            Question question = questionService.findById(questionId).get();
            user.getAnswer().setQuestion(question);
            answerService.save(user.getAnswer());
            userService.save(user);

            grpcService.save("POST", "Users", "OK");

            return new ResponseMessage(true, HttpStatus.OK,
                    "User registered successfully");
        }
        catch (ResourceNotFoundException e){
            grpcService.save("POST", "Users", "ERROR - ResourceNotFound");
            throw new ResourceNotFoundException(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseMessage login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.findBuUsernameOrEmail(loginRequest.getUsernameOrEmail(), loginRequest.getUsernameOrEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

            if (user.getPassword().equals(loginRequest.getPassword())) {
                grpcService.save("POST", "Users", "OK");
                return new ResponseMessage(true, HttpStatus.OK, "Login successfull.");
            } else {
                grpcService.save("POST", "Users", "ERROR - WrongInput");
                throw new WrongInputException("Wrong password!");
            }
        }
        catch (ResourceNotFoundException e){
            grpcService.save("POST", "Users", "ERROR - ResourceNotFound");
            throw new ResourceNotFoundException(e.getMessage());
        }
    }
}

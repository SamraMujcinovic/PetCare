package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.Question;
import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.models.roles.Role;
import ba.unsa.etf.nwt.user_service.models.roles.RoleName;
import ba.unsa.etf.nwt.user_service.requests.LoginRequest;
import ba.unsa.etf.nwt.user_service.requests.RegistrationRequest;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private ValidationsService validationsService;

    //private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/{questionId}")
    public ResponseMessage registration(@PathVariable Long questionId, @RequestBody RegistrationRequest registrationRequest) {

        ResponseMessage rm = validationsService.validateRegistration(registrationRequest);
        if(!rm.getSuccess()){
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        User user = new User(registrationRequest.getName(),registrationRequest.getSurname(),
                registrationRequest.getEmail(), registrationRequest.getUsername(),
                registrationRequest.getPassword(), registrationRequest.getAnswer());

        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleService.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Roles are not set!!"));

        user.setRoles(Collections.singleton(userRole));

        if(!questionService.existsById(questionId)) {
            return new ResponseMessage(false,
                    "Question with given id does not exist!", "BAD_REQUEST");
        }

        Question question = questionService.findById(questionId).get();
        user.getAnswer().setQuestion(question);
        answerService.save(user.getAnswer());
        userService.save(user);

        return new ResponseMessage(true,
                "User registered successfully", "OK");

    }

    @PostMapping("/login")
    public ResponseMessage login(@RequestBody LoginRequest loginRequest) {

        ResponseMessage rm = validationsService.validateLogin(loginRequest);
        if(!rm.getSuccess()){
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }

        try {
            User user = userService.findByUsername(loginRequest.getUsernameOrEmail())
                    .orElseThrow(() -> new RuntimeException("User not found!!"));

            if (user.getPassword().equals(loginRequest.getPassword())) {
                return new ResponseMessage(true, "Login successfull", "OK");
            }

            return new ResponseMessage(false, "Login not successfull, wrong password", "NOT_FOUND");
        }
        catch (RuntimeException e){
            try {
                User user = userService.findByEmail(loginRequest.getUsernameOrEmail())
                        .orElseThrow(() -> new RuntimeException(e.getMessage()));

                if (user.getPassword().equals(loginRequest.getPassword())) {
                    return new ResponseMessage(true, "Login successfull", "OK");
                }

                return new ResponseMessage(false, "Login not successfull, wrong password", "NOT_FOUND");
            }
            catch (RuntimeException e2){
                return new ResponseMessage(false, "Login not successfull, " + e2.getMessage() , "NOT_FOUND");
            }
        }
    }
}

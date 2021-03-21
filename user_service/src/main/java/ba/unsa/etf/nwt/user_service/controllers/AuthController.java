package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.Question;
import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.models.roles.Role;
import ba.unsa.etf.nwt.user_service.models.roles.RoleName;
import ba.unsa.etf.nwt.user_service.requests.LoginRequest;
import ba.unsa.etf.nwt.user_service.requests.RegistrationRequest;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.services.AnswerService;
import ba.unsa.etf.nwt.user_service.services.QuestionService;
import ba.unsa.etf.nwt.user_service.services.RoleService;
import ba.unsa.etf.nwt.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Optional;

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

    //private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/{questionId}")
    public ResponseMessage registration(@PathVariable Long questionId, @RequestBody RegistrationRequest registrationRequest) {

        if(registrationRequest.getName().isEmpty()){
            return new ResponseMessage(false, "Name can't be blank!!",
                    "BAD_REQUEST");
        }

        if(registrationRequest.getSurname().isEmpty()){
            return new ResponseMessage(false, "Surname can't be blank!!",
                    "BAD_REQUEST");
        }

        if(registrationRequest.getUsername().isEmpty()){
            return new ResponseMessage(false, "Username can't be blank!!",
                    "BAD_REQUEST");
        }

        if(registrationRequest.getPassword().isEmpty()){
            return new ResponseMessage(false, "Password can't be blank!!",
                    "BAD_REQUEST");
        }

        if(registrationRequest.getEmail().isEmpty()){
            return new ResponseMessage(false, "Email can't be blank!!",
                    "BAD_REQUEST");
        }

        /*if(registrationRequest.getName().isEmpty() || registrationRequest.getSurname().isEmpty() ||
                registrationRequest.getUsername().isEmpty() || registrationRequest.getPassword().isEmpty() ||
                registrationRequest.getEmail().isEmpty()){
            return new ResponseMessage(false, "Please fill in all fields!!",
                    "BAD_REQUEST");
        }*/

        if(!registrationRequest.getEmail().contains("@")) {
            return new ResponseMessage(false, "Email is not valid!!",
                "BAD_REQUEST");
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

        if(loginRequest.getUsernameOrEmail().isEmpty()){
            return new ResponseMessage(false, "Username/email can't be blank!!",
                    "BAD_REQUEST");
        }

        if(loginRequest.getPassword().isEmpty()){
            return new ResponseMessage(false, "Password can't be blank!!",
                    "BAD_REQUEST");
        }

        /*if(loginRequest.getUsernameOrEmail().isEmpty() || loginRequest.getPassword().isEmpty()){
            return new ResponseMessage(false, "Please fill in all fields!!",
                    "BAD_REQUEST");
        }*/

        try {
            User user = userService.findByUsername(loginRequest.getUsernameOrEmail())
                    .orElseThrow(() -> new RuntimeException("User not found!!"));

            if (user.getPassword().equals(loginRequest.getPassword())) {
                return new ResponseMessage(true, "Login successfull", "OK");
            }

            return new ResponseMessage(false, "Login not successfull, wrong password", "NOT OK");
        }
        catch (RuntimeException e){
            try {
                User user = userService.findByEmail(loginRequest.getUsernameOrEmail())
                        .orElseThrow(() -> new RuntimeException(e.getMessage()));

                if (user.getPassword().equals(loginRequest.getPassword())) {
                    return new ResponseMessage(true, "Login successfull", "OK");
                }

                return new ResponseMessage(false, "Login not successfull, wrong password", "NOT OK");
            }
            catch (RuntimeException e2){
                return new ResponseMessage(false, "Login not successfull, " + e2.getMessage() , "NOT OK");
            }
        }
    }
}

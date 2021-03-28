package ba.unsa.etf.nwt.user_service.controller;

import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
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

    //private final PasswordEncoder passwordEncoder;

    @PostMapping("/register/{questionId}")
    public ResponseMessage registration(@PathVariable Long questionId, @Valid @RequestBody RegistrationRequest registrationRequest) {

        User user = new User(registrationRequest.getName(),registrationRequest.getSurname(),
                registrationRequest.getEmail(), registrationRequest.getUsername(),
                registrationRequest.getPassword(), registrationRequest.getAnswer());

        //user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleService.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new ResourceNotFoundException("Roles are not set!!"));

        user.setRoles(Collections.singleton(userRole));

        if(!questionService.existsById(questionId)) {
            throw new ResourceNotFoundException("Question with given id does not exist!");
        }

        Question question = questionService.findById(questionId).get();
        user.getAnswer().setQuestion(question);
        answerService.save(user.getAnswer());
        userService.save(user);

        return new ResponseMessage(true, HttpStatus.OK,
                "User registered successfully");

    }

    @PostMapping("/login")
    public ResponseMessage login(@Valid @RequestBody LoginRequest loginRequest) {
        //TODO popraviti jos....

        try {
            User user = userService.findByUsername(loginRequest.getUsernameOrEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

            if (user.getPassword().equals(loginRequest.getPassword())) {
                return new ResponseMessage(true, HttpStatus.OK, "Login successfull");
            }

            return new ResponseMessage(false, HttpStatus.NOT_FOUND,"Login not successfull, wrong password");
        }
        catch(ResourceNotFoundException e){
            User user = userService.findByEmail(loginRequest.getUsernameOrEmail())
                    .orElseThrow(() -> new ResourceNotFoundException(e.getMessage()));

            if (user.getPassword().equals(loginRequest.getPassword())) {
                return new ResponseMessage(true, HttpStatus.OK, "Login successfull");
            }

            throw new ResourceNotFoundException("Login not successfull, wrong password");
        }
    }
}

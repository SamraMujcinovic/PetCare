package ba.unsa.etf.nwt.user_service.controller;

import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.exception.WrongInputException;
import ba.unsa.etf.nwt.user_service.model.User;
import ba.unsa.etf.nwt.user_service.request.UserProfileRequest;
import ba.unsa.etf.nwt.user_service.request.UserRequest;
import ba.unsa.etf.nwt.user_service.response.AvailabilityResponse;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import ba.unsa.etf.nwt.user_service.response.UserProfileResponse;
import ba.unsa.etf.nwt.user_service.security.CurrentUser;
import ba.unsa.etf.nwt.user_service.security.UserPrincipal;
import ba.unsa.etf.nwt.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/users/{username}")
    public UserProfileResponse getUserProfile(@PathVariable(value = "username") String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        return new UserProfileResponse(user.getName(), user.getSurname(), user.getUsername(), user.getEmail());
    }

    @GetMapping("/user/usernameCheck")
    public AvailabilityResponse checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return new AvailabilityResponse(!userService.existsByUsername(username));
    }

    @GetMapping("/user/emailCheck")
    public AvailabilityResponse checkEmailAvailability(@RequestParam(value = "email") String email) {
        return new AvailabilityResponse(!userService.existsByEmail(email));
    }

    //user and admin
    @GetMapping("/user/me")
    public UserProfileResponse getCurrentUser(@CurrentUser UserPrincipal currentUser){
        return new UserProfileResponse(currentUser.getName(), currentUser.getSurname(), currentUser.getUsername(), currentUser.getEmail());
    }

    //user and admin
    @PostMapping("/user/update")
    public ResponseMessage updateUserProfile(@Valid @RequestBody UserProfileRequest userProfileRequest, @CurrentUser UserPrincipal currentUser){

        if(currentUser == null){
            throw new ResourceNotFoundException("Current User not found!");
        }

        if(!currentUser.getEmail().equals(userProfileRequest.getEmail())){
            throw new WrongInputException("Email not the same as current users!");
        }

        User user = userService.findByEmail(userProfileRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        user.setName(userProfileRequest.getName());
        user.setSurname(userProfileRequest.getSurname());
        user.setUsername(userProfileRequest.getUsername());
        userService.save(user);
        return new ResponseMessage(true, HttpStatus.OK, "Profile successfully updated.");
    }

    //user and admin
    @DeleteMapping("/user/delete")
    public ResponseMessage deleteUser(@Valid @RequestBody UserRequest userRequest, @CurrentUser UserPrincipal currentUser){

        if(currentUser == null){
            throw new ResourceNotFoundException("Current User not found!");
        }

        if(!currentUser.getEmail().equals(userRequest.getEmail())){
            throw new WrongInputException("Email not the same as current users!");
        }

        User user = userService.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            userService.delete(user);
            return new ResponseMessage(true, HttpStatus.OK, "You have successfully deleted your account.");
        } else {
            throw new WrongInputException("Wrong password!");
        }
    }
}

package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.requests.UserProfileRequest;
import ba.unsa.etf.nwt.user_service.responses.AvailabilityResponse;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.responses.UserProfileResponse;
import ba.unsa.etf.nwt.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    //admin
    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    //admin
    @GetMapping("/users/{username}")
    public UserProfileResponse getUserProfile(@PathVariable(value = "username") String username) {
        try {
            User user = userService.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found!!"));

            return new UserProfileResponse(user.getName(), user.getSurname(), user.getUsername(), user.getEmail());
        }
        catch(RuntimeException e){
            return new UserProfileResponse(e.getMessage(), "/", "/", "/");
        }
    }

    @GetMapping("/user/usernameCheck")
    public AvailabilityResponse checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return new AvailabilityResponse(!userService.existsByUsername(username));
    }

    @GetMapping("/user/emailCheck")
    public AvailabilityResponse checkEmailAvailability(@RequestParam(value = "email") String email) {
        return new AvailabilityResponse(!userService.existsByEmail(email));
    }

    //user
    @GetMapping("/user/me")
    public UserProfileResponse getCurrentUser(/*@CurrentUser UserPrincipal currentUser*/){
        return new UserProfileResponse("Amila", "Lakovic", "alakovic1", "alakovic1@etf.unsa.ba");
    }

    //user
    @PostMapping("/user/update")
    public ResponseMessage updateUserProfile(@RequestBody UserProfileRequest userProfileRequest){

        if(userProfileRequest.getName().length() < 2 || userProfileRequest.getName().length() > 50){
            return new ResponseMessage(false,
                    "Name not valid (at least 2 characters)!!",
                    "BAD_REQUEST");
        }

        if(userProfileRequest.getSurname().length() < 2 || userProfileRequest.getSurname().length() > 50){
            return new ResponseMessage(false, "Surname not valid (at least 2 characters)!!",
                    "BAD_REQUEST");
        }

        if(userProfileRequest.getEmail().isEmpty() || !userProfileRequest.getEmail().contains("@") || userProfileRequest.getEmail().length() > 100){
            return new ResponseMessage(false, "Email is not valid!!",
                    "BAD_REQUEST");
        }

        if(userProfileRequest.getUsername().length() < 4 || userProfileRequest.getUsername().length() > 40){
            return new ResponseMessage(false, "Username not valid (at least 4 characters)!!",
                    "BAD_REQUEST");
        }

        if(userService.existsByUsername(userProfileRequest.getUsername())) {
            return new ResponseMessage(false, "This username is already taken!!",
                    "BAD_REQUEST");
        }

        try {
            User user = userService.findByEmail(userProfileRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setName(userProfileRequest.getName());
            user.setSurname(userProfileRequest.getSurname());
            user.setUsername(userProfileRequest.getUsername());
            userService.save(user);
            return new ResponseMessage(true, "Profile successfully updated!!", "OK");
        }
        catch (RuntimeException e){
            return new ResponseMessage(false, e.getMessage(),
                    "BAD_REQUEST");
        }
    }
}

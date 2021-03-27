package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.requests.UserProfileRequest;
import ba.unsa.etf.nwt.user_service.requests.UserRequest;
import ba.unsa.etf.nwt.user_service.responses.AvailabilityResponse;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import ba.unsa.etf.nwt.user_service.responses.UserProfileResponse;
import ba.unsa.etf.nwt.user_service.services.UserService;
import ba.unsa.etf.nwt.user_service.services.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ValidationsService validationsService;

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
        ResponseMessage rm = validationsService.validateUserProfile(userProfileRequest);
        if(!rm.getSuccess()){
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
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

    @DeleteMapping("/user/delete")
    public ResponseMessage deleteUser(@RequestBody UserRequest userRequest){
        return userService.deleteUser(userRequest);
    }
}

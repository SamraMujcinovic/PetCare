package ba.unsa.etf.nwt.user_service.controllers;

import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.responses.AvailabilityResponse;
import ba.unsa.etf.nwt.user_service.responses.UserProfileResponse;
import ba.unsa.etf.nwt.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    //user!!
    @GetMapping("/user/me")
    public void getCurrentUser(){

    }
    /*public UserProfileResponse getCurrentUser(@CurrentUser UserPrincipal currentUser) {
       return new UserProfileResponse(currentUser.getName(), currentUser.getSurname(), currentUser.getUsername(), currentUser.getEmail());
    }*/
}

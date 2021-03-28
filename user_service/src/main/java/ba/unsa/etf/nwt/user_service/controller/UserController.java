package ba.unsa.etf.nwt.user_service.controller;

import ba.unsa.etf.nwt.user_service.exception.ResourceNotFoundException;
import ba.unsa.etf.nwt.user_service.model.User;
import ba.unsa.etf.nwt.user_service.request.UserProfileRequest;
import ba.unsa.etf.nwt.user_service.request.UserRequest;
import ba.unsa.etf.nwt.user_service.response.AvailabilityResponse;
import ba.unsa.etf.nwt.user_service.response.ResponseMessage;
import ba.unsa.etf.nwt.user_service.response.UserProfileResponse;
import ba.unsa.etf.nwt.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

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

    //user
    @GetMapping("/user/me")
    public UserProfileResponse getCurrentUser(/*@CurrentUser UserPrincipal currentUser*/){
        return new UserProfileResponse("Amila", "Lakovic", "alakovic1", "alakovic1@etf.unsa.ba");
    }

    //user
    @PostMapping("/user/update")
    public ResponseMessage updateUserProfile(@Valid @RequestBody UserProfileRequest userProfileRequest){
        User user = userService.findByEmail(userProfileRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(userProfileRequest.getName());
        user.setSurname(userProfileRequest.getSurname());
        user.setUsername(userProfileRequest.getUsername());
        userService.save(user);
        return new ResponseMessage(true, HttpStatus.OK,"Profile successfully updated!!");
    }

    @DeleteMapping("/user/delete")
    public ResponseMessage deleteUser(@Valid @RequestBody UserRequest userRequest){
        User user = userService.findByEmail(userRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(user.getPassword().equals(userRequest.getPassword())){
            userService.delete(user);
            return new ResponseMessage(true, HttpStatus.OK,"You have successfully deleted your account!!");
        }

        throw new ResourceNotFoundException("Wrong password!!");
    }
}

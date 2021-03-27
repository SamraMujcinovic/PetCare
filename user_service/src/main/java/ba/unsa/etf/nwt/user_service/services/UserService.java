package ba.unsa.etf.nwt.user_service.services;

import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.repository.UserRepository;
import ba.unsa.etf.nwt.user_service.requests.UserRequest;
import ba.unsa.etf.nwt.user_service.responses.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private ValidationsService validationsService;

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public Boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public ResponseMessage deleteUser(UserRequest userRequest) {
        ResponseMessage rm = validationsService.validateUserProfile2(userRequest);
        if(!rm.getSuccess()){
            return new ResponseMessage(false, rm.getMessage(), rm.getStatus());
        }
        try {
            User user = userRepository.findByEmail(userRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if(user.getPassword().equals(userRequest.getPassword())){
                userRepository.delete(user);
                return new ResponseMessage(true, "You have successfully deleted your account!!", "OK");
            }

            return new ResponseMessage(false, "Wrong password!!", "BAD_REQUEST");
        }
        catch(RuntimeException e){
            return new ResponseMessage(false, e.getMessage(), "NOT_FOUND");
        }
    }
}

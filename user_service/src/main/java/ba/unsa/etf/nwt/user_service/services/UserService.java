package ba.unsa.etf.nwt.user_service.services;

import ba.unsa.etf.nwt.user_service.models.User;
import ba.unsa.etf.nwt.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user){
        return userRepository.save(user);
    }
}

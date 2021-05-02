package ba.unsa.etf.nwt.comment_service.security;

import ba.unsa.etf.nwt.comment_service.security.user_model.User;
import ba.unsa.etf.nwt.comment_service.service.CommunicationsService;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final CommunicationsService communicationsService;

    public CustomUserDetailsService(CommunicationsService communicationsService) {
        this.communicationsService = communicationsService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        // access user_service database
        RestTemplate restTemplate = new RestTemplate();

        User user = restTemplate.getForObject(communicationsService.getUri("user_service")
                + "/auth/load/usernameEmail/" + usernameOrEmail, User.class);

        System.out.println("USERRRRR  " + user.getEmail());

        return UserPrincipal.create(user);
    }

    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {

        RestTemplate restTemplate = new RestTemplate();

        User user = restTemplate.getForObject(communicationsService.getUri("user_service")
                + "/auth/load/id/" + id, User.class);

        System.out.println("USERRRRR2  " + user.getEmail());

        return UserPrincipal.create(user);
    }
}

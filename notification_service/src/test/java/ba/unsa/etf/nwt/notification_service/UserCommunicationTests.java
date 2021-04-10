package ba.unsa.etf.nwt.notification_service;

import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import ba.unsa.etf.nwt.notification_service.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserCommunicationTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private NotificationRepository notificationRepository;

    //@InjectMocks
    //private final NotificationService commentService = new NotificationService(notificationRepository);

    @Test
    public void getCurrentUserId() {
        Mockito
                .when(restTemplate.getForEntity(
                        "http://localhost:8080/user/me/id", Long.class))
          .thenReturn(new ResponseEntity(HttpStatus.OK));
    }
}

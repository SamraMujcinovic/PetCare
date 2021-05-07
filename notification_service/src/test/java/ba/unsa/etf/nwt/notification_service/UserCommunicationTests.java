package ba.unsa.etf.nwt.notification_service;

import ba.unsa.etf.nwt.notification_service.repository.NotificationRepository;
import ba.unsa.etf.nwt.notification_service.service.CommunicationsService;
import ba.unsa.etf.nwt.notification_service.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserCommunicationTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private NotificationRepository notificationRepository;

    @Autowired
    private CommunicationsService communicationsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCurrentUserId() {
        /*Mockito
                .when(restTemplate.getForEntity(
                        communicationsService.getUri("user_service")
                                + "/user/me/id", String.class))
                .thenReturn(new ResponseEntity(1L, HttpStatus.OK));*/
    }

    @Test
    void AddNotification() throws Exception {
        /*String newComment = "{\n" +
                "    \"content\": \"Notification 1\",\n" +
                "    \"read\": false,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));*/
    }

    @Test
    void AddAndGetNotification() throws Exception {
        /*String newComment = "{\n" +
                "    \"content\": \"Notification 1\",\n" +
                "    \"read\": false,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        RequestBuilder requestBuilder2 = MockMvcRequestBuilders.get("/notifications")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder2)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));*/
    }
}

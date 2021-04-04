package ba.unsa.etf.nwt.comment_service;

import ba.unsa.etf.nwt.comment_service.model.Comment;
import ba.unsa.etf.nwt.comment_service.model.sectionRole.MainRole;
import ba.unsa.etf.nwt.comment_service.model.sectionRole.SectionRoleName;
import ba.unsa.etf.nwt.comment_service.repository.CommentRepository;
import ba.unsa.etf.nwt.comment_service.service.CommentService;
import ba.unsa.etf.nwt.comment_service.service.MainRoleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserCommunicationTests {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MainRoleService mainRoleService;

    @Mock
    private CommentRepository commentRepository;

    //private MockRestServiceServer mockServer;
    //private ObjectMapper mapper = new ObjectMapper();

    //@Before("")
    //public void init() {
        //mockServer = MockRestServiceServer.createServer(restTemplate);
    //}

    @InjectMocks
    private final CommentService commentService = new CommentService(commentRepository, mainRoleService);

    @Test
    public void getCurrentUserUsernameFromUserService() {
        String username = "alakovic1";
        Mockito
                .when(restTemplate.getForEntity(
                        "http://localhost:8080/user/me/username", String.class))
          .thenReturn(new ResponseEntity(username, HttpStatus.OK));
    }

    @Test
    public void getUsernameByUsername(){
        String username = "user";
        Mockito
                .when(restTemplate.getForEntity(
                        "http://localhost:8080/user/" + username, String.class))
                .thenReturn(new ResponseEntity(username, HttpStatus.OK));

        //List<Comment> comments = commentService.getComment();
        //assertEquals("UNKNOWN", comments.get(0).getUsername());

        /*mockServer.expect(ExpectedCount.once(),
                requestTo(new URI("http://localhost:8080/user/" + username)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(username))
                );*/
    }
}
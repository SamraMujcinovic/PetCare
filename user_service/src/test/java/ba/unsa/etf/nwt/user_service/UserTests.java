package ba.unsa.etf.nwt.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class UserTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void GetAllUsersInJSON() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void GetUserWithUsernameSuccess() throws Exception{

        String username = "alakovic1";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{username}", username)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"name\": \"Amila\",\n" +
                        "  \"surname\": \"Lakovic\",\n" +
                        "  \"username\": \"alakovic1\",\n" +
                        "  \"email\": \"alakovic1@etf.unsa.ba\"\n" +
                        "}"));
    }

    @Test
    void GetUserWithUsernameNotFound() throws Exception{

        String username = "username";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{username}", username)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"name\": \"User not found!!\",\n" +
                        "  \"surname\": \"/\",\n" +
                        "  \"username\": \"/\",\n" +
                        "  \"email\": \"/\"\n" +
                        "}"));
    }

    @Test
    void CheckUsernameAvailable() throws Exception{

        String username = "username";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/usernameCheck/")
                .param("username", username);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    void CheckUsernameNotAvailable() throws Exception{

        String username = "alakovic1";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/usernameCheck/")
                .param("username", username);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    void CheckEmailAvailable() throws Exception{

        String email = "email@etf.unsa.ba";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/emailCheck/")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    void CheckEmailNotAvailable() throws Exception{

        String email = "alakovic1@etf.unsa.ba";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/emailCheck/")
                .param("email", email)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    void UpdateUserProfileSuccess() throws Exception{

        String newUserInfo = "{\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\",\n" +
                "  \"name\": \"amilaaa\",\n" +
                "  \"surname\": \"lakovic\",\n" +
                "  \"username\": \"newusername\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserInfo);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"Profile successfully updated!!\",\n" +
                        "  \"status\": \"OK\"\n" +
                        "}"));
    }

    @Test
    void UpdateUserProfileUserNotFound() throws Exception{

        String newUserInfo = "{\n" +
                "  \"email\": \"alakovic111@etf.unsa.ba\",\n" +
                "  \"name\": \"amilaaa\",\n" +
                "  \"surname\": \"lakovic\",\n" +
                "  \"username\": \"newusername\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserInfo);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"User not found\",\n" +
                        "  \"status\": \"BAD_REQUEST\"\n" +
                        "}"));
    }

    @Test
    void UpdateUserProfileEmailNotValid() throws Exception{

        String newUserInfo = "{\n" +
                "  \"email\": \"alakovic1\",\n" +
                "  \"name\": \"amilaaa\",\n" +
                "  \"surname\": \"lakovic\",\n" +
                "  \"username\": \"newusername\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserInfo);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Email is not valid!!\",\n" +
                        "  \"status\": \"BAD_REQUEST\"\n" +
                        "}"));
    }

    @Test
    void UpdateUserProfileUsernameNotValid() throws Exception{

        String newUserInfo = "{\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\",\n" +
                "  \"name\": \"amilaaa\",\n" +
                "  \"surname\": \"lakovic\",\n" +
                "  \"username\": \"\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserInfo);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Username not valid (at least 4 characters)!!\",\n" +
                        "  \"status\": \"BAD_REQUEST\"\n" +
                        "}"));
    }

    @Test
    void UpdateUserProfileSurnameNotValid() throws Exception{

        String newUserInfo = "{\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\",\n" +
                "  \"name\": \"amilaaa\",\n" +
                "  \"surname\": \"\",\n" +
                "  \"username\": \"username\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserInfo);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Surname not valid (at least 2 characters)!!\",\n" +
                        "  \"status\": \"BAD_REQUEST\"\n" +
                        "}"));
    }

    @Test
    void UpdateUserProfileNameNotValid() throws Exception{

        String newUserInfo = "{\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\",\n" +
                "  \"name\": \"\",\n" +
                "  \"surname\": \"lakovic\",\n" +
                "  \"username\": \"username\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newUserInfo);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Name not valid (at least 2 characters)!!\",\n" +
                        "  \"status\": \"BAD_REQUEST\"\n" +
                        "}"));
    }

}

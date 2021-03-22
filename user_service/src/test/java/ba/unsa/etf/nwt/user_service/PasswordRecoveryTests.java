package ba.unsa.etf.nwt.user_service;

import ba.unsa.etf.nwt.user_service.repository.UserRepository;
import ba.unsa.etf.nwt.user_service.services.UserService;
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
public class PasswordRecoveryTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void GetSecurityQuestionForRecoveryPasswordSuccess() throws Exception{

        String input = "{\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/securityquestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"Valid email, question found!!\",\n" +
                        "  \"status\": \"OK\",\n" +
                        "  \"question\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"title\": \"What is the name of the town where you were born?\",\n" +
                        "    \"description\": \"Home town\"\n" +
                        "  }\n" +
                        "}"));
    }

    @Test
    void GetSecurityQuestionForRecoveryPasswordEmailNotValid() throws Exception{

        String input = "{\n" +
                "  \"email\": \"alakovic1\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/securityquestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Email is not valid!!\",\n" +
                        "  \"status\": \"BAD_REQUEST\",\n" +
                        "  \"question\": {\n" +
                        "    \"id\": null,\n" +
                        "    \"title\": null,\n" +
                        "    \"description\": null\n" +
                        "  }\n" +
                        "}"));
    }

    @Test
    void GetSecurityQuestionForRecoveryPasswordUserNotFound() throws Exception{

        String input = "{\n" +
                "  \"email\": \"alakovic@etf.unsa.ba\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/securityquestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"User not found\",\n" +
                        "  \"status\": \"NOT_FOUND\",\n" +
                        "  \"question\": {\n" +
                        "    \"id\": null,\n" +
                        "    \"title\": null,\n" +
                        "    \"description\": null\n" +
                        "  }\n" +
                        "}"));
    }

    @Test
    void AnswerQuestionCorrectly() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"Sarajevo\",\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/answerQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"You have successfully answered the question\",\n" +
                        "  \"status\": \"OK\"\n" +
                        "}"));
    }

    @Test
    void AnswerQuestionNotCorrectly() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"odgovor\",\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/answerQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Wrong answer!!\",\n" +
                        "  \"status\": \"NOT_FOUND\"\n" +
                        "}"));
    }

    @Test
    void AnswerQuestionEmailNotValid() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"odgovor\",\n" +
                "  \"email\": \"alakovic1\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/answerQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
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
    void AnswerQuestionUserNotFound() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"odgovor\",\n" +
                "  \"email\": \"email@etf.unsa.ba\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/answerQuestion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"User not found\",\n" +
                        "  \"status\": \"NOT_FOUND\"\n" +
                        "}"));
    }

    @Test
    void CreateNewPasswordSuccess() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"Sarajevo\",\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\",\n" +
                "  \"newPassword\": \"newPassword\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"You have successfully recovered your password\",\n" +
                        "  \"status\": \"OK\"\n" +
                        "}"));
    }

    @Test
    void CreateNewPasswordWrongAnswer() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"odgovor\",\n" +
                "  \"email\": \"alakovic1@etf.unsa.ba\",\n" +
                "  \"newPassword\": \"newPassword\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Wrong answer!!\",\n" +
                        "  \"status\": \"NOT_FOUND\"\n" +
                        "}"));
    }

    @Test
    void CreateNewPasswordEmailNotValid() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"odgovor\",\n" +
                "  \"email\": \"alakovic1\",\n" +
                "  \"newPassword\": \"newPassword\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
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
    void CreateNewPasswordUserNotFound() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"odgovor\",\n" +
                "  \"email\": \"email@etf.unsa.ba\",\n" +
                "  \"newPassword\": \"newPassword\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"User not found\",\n" +
                        "  \"status\": \"NOT_FOUND\"\n" +
                        "}"));
    }

    @Test
    void CreateNewPasswordPaswordNotValid() throws Exception{

        String input = "{\n" +
                "  \"answer\": \"odgovor\",\n" +
                "  \"email\": \"email@etf.unsa.ba\",\n" +
                "  \"newPassword\": \"\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/recovery/newPassword")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"New password not valid (must have at least 6 characters)!!\",\n" +
                        "  \"status\": \"BAD_REQUEST\"\n" +
                        "}"));
    }

}

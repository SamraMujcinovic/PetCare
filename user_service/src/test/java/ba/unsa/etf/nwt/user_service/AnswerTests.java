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
public class AnswerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void GetAnswerOfQuestionTest() throws Exception{

        Long questionId = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/questions/{questionId}/answers", questionId)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":7,\"text\":\"Sarajevo\",\"question\":{\"id\":1,\"title\":\"What is the name of the town where you were born?\",\"description\":\"Home town\"}},{\"id\":13,\"text\":\"Zenica\",\"question\":{\"id\":1,\"title\":\"What is the name of the town where you were born?\",\"description\":\"Home town\"}},{\"id\":16,\"text\":\"string\",\"question\":{\"id\":1,\"title\":\"What is the name of the town where you were born?\",\"description\":\"Home town\"}}]"));
    }

    @Test
    void AnswerQuestionSuccessTest() throws Exception{

        Long questionId = 1L;

        String newAnswer = "{\n" +
                "  \"text\": \"string\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions/{questionId}/answer", questionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAnswer);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":true,\"message\":\"Answer added successfully!!\",\"status\":\"OK\"}"));
    }

    @Test
    void AnswerQuestionBlankAnswerTest() throws Exception{

        Long questionId = 1L;

        String newAnswer = "{\n" +
                "  \"text\": \"\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions/{questionId}/answer", questionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAnswer);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":false,\"message\":\"Answer not valid!!\",\"status\":\"BAD_REQUEST\"}"));
    }
}

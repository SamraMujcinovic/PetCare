package ba.unsa.etf.nwt.user_service;

import ba.unsa.etf.nwt.user_service.repository.QuestionRepository;
import ba.unsa.etf.nwt.user_service.service.QuestionService;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class QuestionTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void GetAllQuestionsInJSON() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/questions")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void CreateNewQuestionTestSuccess() throws Exception{

        String newQuestion = " { \"description\":\"string\", \"title\":\"string\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newQuestion);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":true,\"message\":\"Question added successfully!!\",\"status\":\"OK\"}"));
    }

    @Test
    void CreateNewQuestionTestNotSuccess1() throws Exception{

        String newQuestion = " { \"description\":\"\", \"title\":\"string\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newQuestion);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Description can't be blank!!\",\n" +
                        "  \"status\": \"BAD_REQUEST\"\n" +
                        "}\n"));
    }

    @Test
    void CreateNewQuestionTestNotSuccess2() throws Exception{

        String newQuestion = " { \"description\":\"string\", \"title\":\"\" }";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newQuestion);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": false,\n" +
                        "  \"message\": \"Title can't be blank!!\",\n" +
                        "  \"status\": \"BAD_REQUEST\"\n" +
                        "}\n"));
    }
}

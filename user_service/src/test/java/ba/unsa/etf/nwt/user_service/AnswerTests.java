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
    void GetAnswerOfQuestion() throws Exception{

        Long questionId = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/questions/{questionId}/answers", questionId)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    void AnswerQuestionSuccess() throws Exception{

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
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"status\": \"OK\",\n" +
                        "  \"message\": \"Answer added successfully!!\"\n" +
                        "}"));
    }

    @Test
    void AnswerQuestionBlankAnswer() throws Exception{

        Long questionId = 1L;

        String newAnswer = "{\n" +
                "  \"text\": \"\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions/{questionId}/answer", questionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newAnswer);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"responseMessage\": {\n" +
                        "    \"success\": false,\n" +
                        "    \"status\": \"BAD_REQUEST\",\n" +
                        "    \"message\": \"Validation Failed\"\n" +
                        "  },\n" +
                        "  \"details\": [\n" +
                        "    \"Answer can't be blank\"\n" +
                        "  ]\n" +
                        "}"));
    }
}

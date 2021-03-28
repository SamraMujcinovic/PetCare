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

        String newQuestion = "{\n" +
                "  \"description\": \"string\",\n" +
                "  \"title\": \"string\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newQuestion);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"status\": \"OK\",\n" +
                        "  \"message\": \"Question added successfully.\"\n" +
                        "}"));
    }

    @Test
    void CreateNewQuestionTestNotSuccess1() throws Exception{

        String newQuestion = "{\n" +
                "  \"description\": \"\",\n" +
                "  \"title\": \"string\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newQuestion);
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
                        "    \"Description can't be blank\"\n" +
                        "  ]\n" +
                        "}"));
    }

    @Test
    void CreateNewQuestionTestNotSuccess2() throws Exception{

        String newQuestion = "{\n" +
                "  \"description\": \"string\",\n" +
                "  \"title\": \"\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newQuestion);
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
                        "    \"Title can't be blank\"\n" +
                        "  ]\n" +
                        "}"));
    }

    @Test
    void CreateNewQuestionTestNotSuccess3() throws Exception{

        String newQuestion = "{\n" +
                "  \"description\": \"\",\n" +
                "  \"title\": \"\"\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newQuestion);
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
                        "    \"Description can't be blank\",\n" +
                        "    \"Title can't be blank\"\n" +
                        "  ]\n" +
                        "}"));
    }
}

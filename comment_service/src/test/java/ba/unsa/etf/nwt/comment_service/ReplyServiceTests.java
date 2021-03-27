package ba.unsa.etf.nwt.comment_service;

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
class ReplyServiceTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void GetAllRepliesInJSON() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reply")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void CreateNewReply() throws Exception {
        Long commentId = 1L;

        String newReply = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"content\": \"Pet care\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reply/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReply);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "    \"success\": true,\n" +
                        "    \"message\": \"Reply added successfully!!\",\n" +
                        "    \"status\": \"OK\"\n" +
                        "}\n"));
    }


    @Test
    void CreateNewReplySmallContent() throws Exception {
        Long commentId = 1L;

        String newReply = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"content\": \"P\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reply/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReply);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "    \"success\": false,\n" +
                        "    \"message\": \"Content must be between 2 and 1000 characters!!\",\n" +
                        "    \"status\": \"BAD_REQUEST\"\n" +
                        "}\n"));
    }

    void CreateNewReplyBlankContent() throws Exception {
        Long commentId = 1L;

        String newReply = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"content\": \"\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reply/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReply);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "    \"success\": false,\n" +
                        "    \"message\": \"Content can't be blank!!\",\n" +
                        "    \"status\": \"BAD_REQUEST\"\n" +
                        "}\n"));
    }

    @Test
    void GetAllCommentRepliesInJSON() throws Exception {
        Long commentID = 1L;
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reply/comment/{commentID}", commentID)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void UpdateReply() throws Exception {
        Long replyId = 1L;
        String newReply = "{\n" +
                "    \"content\": \"con\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/reply/{replyId}", replyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReply);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void UpdateReplySmallContent() throws Exception {
        Long replyId = 1L;
        String newReply = "{\n" +
                "    \"content\": \"c\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/reply/{replyId}", replyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReply);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void UpdateReplyBlankContent() throws Exception {
        Long replyId = 1L;
        String newReply = "{\n" +
                "    \"content\": \"\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/reply/{replyId}", replyId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReply);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}

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
class CommentServiceTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void GetAllCommentsInJSON() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comment")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void CreateNewComment() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"title\": \"Pet care\",\n" +
                "    \"content\": \"How to take care of my pet?\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "    \"success\": true,\n" +
                        "    \"message\": \"Comment added successfully!!\",\n" +
                        "    \"status\": \"OK\"\n" +
                        "}\n"));
    }

    @Test
    void CreateNewCommentSmallTitle() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"title\": \"P\",\n" +
                "    \"content\": \"How to take care of my pet?\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\": {\n" +
                        "        \"success\": false,\n" +
                        "        \"status\": \"BAD_REQUEST\",\n" +
                        "        \"message\": \"Validation Failed\"\n" +
                        "    },\n" +
                        "    \"details\": [\n" +
                        "        \"Title must be between 2 and 100 characters!!\"\n" +
                        "    ]\n" +
                        "}\n}\n"));
    }

    @Test
    void CreateNewCommentSmallContent() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"title\": \"Pet care\",\n" +
                "    \"content\": \"H\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content must be between 2 and 1000 characters!!\"]}"));
    }

    @Test
    void CreateNewCommentSmallContentSmallTitle() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"title\": \"P\",\n" +
                "    \"content\": \"H\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content must be between 2 and 1000 characters!!\",\"Title must be between 2 and 100 characters!!\"]}"));
    }

    @Test
    void CreateNewCommentNoUserIdSmallContentAndTitle() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"title\": \"P\",\n" +
                "    \"content\": \"H\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Title must be between 2 and 100 characters!!\",\"Content must be between 2 and 1000 characters!!\"]}"));
    }

    @Test
    void CreateNewCommentNoUserIdSmallContent() throws Exception {
        Long mainRoleId = 1L;
        String newComment = "{\n" +
                "    \"title\": \"Pet care\",\n" +
                "    \"content\": \"H\"\n" +
                "}\n";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content must be between 2 and 1000 characters!!\"]}"));
    }

    @Test
    void CreateNewCommentNoUserIdSmallTitle() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"title\": \"P\",\n" +
                "    \"content\": \"Content\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Title must be between 2 and 100 characters!!\"]}"));
    }

    @Test
    void CreateNewCommentNoUserId() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"title\": \"Pet care\",\n" +
                "    \"content\": \"Content\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":true,\"status\":\"OK\",\"message\":\"Comment added successfully!!\"}"));
    }

    @Test
    void CreateNewCommentNoTitle() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"content\": \"How to take care of my pet?\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Title can't be blank\"]}\n"));
    }

    @Test
    void CreateNewCommentNoTitleSmallContent() throws Exception {
        Long mainRoleId = 1L;

        String newComment = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"content\": \"H\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/comment/{mainRoleId}", mainRoleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content must be between 2 and 1000 characters!!\",\"Title can't be blank\"]}\n"));
    }

    @Test
    void GetOneCommentInJSON() throws Exception {
        Long commentID = 1L;
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comment/{commentID}", commentID)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void UpdateComment() throws Exception {
        Long commentId = 1L;
        String newComment = "{\n" +
                "    \"title\": \"Test\",\n" +
                "    \"content\": \"Update\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "    \"success\": true,\n" +
                        "    \"message\": \"Comment updated successfully!!\",\n" +
                        "    \"status\": \"OK\"\n" +
                        "}\n"));
    }

    @Test
    void UpdateCommentSmallTitle() throws Exception {
        Long commentId = 1L;
        String newComment = "{\n" +
                "    \"title\": \"T\",\n" +
                "    \"content\": \"Update\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\": {\n" +
                        "        \"success\": false,\n" +
                        "        \"status\": \"BAD_REQUEST\",\n" +
                        "        \"message\": \"Validation Failed\"\n" +
                        "    },\n" +
                        "    \"details\": [\n" +
                        "        \"Title must be between 2 and 100 characters!!\"\n" +
                        "    ]\n" +
                        "}\n}\n"));
    }

    @Test
    void UpdateCommentSmallContent() throws Exception {
        Long commentId = 1L;
        String newComment = "{\n" +
                "    \"title\": \"Pet\",\n" +
                "    \"content\": \"U\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content must be between 2 and 1000 characters!!\"]}"));
    }

    @Test
    void UpdateCommentSmallContentSmallTitle() throws Exception {
        Long commentId = 1L;
        String newComment = "{\n" +
                "    \"title\": \"P\",\n" +
                "    \"content\": \"U\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content must be between 2 and 1000 characters!!\",\"Title must be between 2 and 100 characters!!\"]}"));
    }

    @Test
    void UpdateCommentSmallContentNoTitle() throws Exception {
        Long commentId = 1L;
        String newComment = "{\n" +
                "    \"content\": \"U\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content must be between 2 and 1000 characters!!\",\"Title can't be blank\"]}"));
    }

    @Test
    void UpdateCommentNoTitle() throws Exception {
        Long commentId = 1L;
        String newComment = "{\n" +
                "    \"content\": \"Content\"\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/comment/{commentId}", commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newComment);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Title can't be blank\"]}"));
    }

    @Test
    void GetAllCategoryCommentsInJSON() throws Exception {
        Long roleType = 1L;
        Long categoryID = 1L;
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/comment/category/{roleType}/{categoryID}", roleType, categoryID)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}

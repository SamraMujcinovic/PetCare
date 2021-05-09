package ba.unsa.etf.nwt.notification_service;

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
class NotificationServiceTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void GetAllNotificationsInJSON() throws Exception {
        /*RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/notifications")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));*/
    }

    @Test
    void CreateNewNotification() throws Exception {
        /*String newNotification = "{\n" +
                "    \"content\": \"Notification 1\",\n" +
                "    \"userID\": 1,\n" +
                "    \"read\": false,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"Notification added successfully!!\",\n" +
                        "  \"status\": \"OK\"\n" +
                        "}"));*/
    }

    @Test
    void CreateNewNotificationBlankContent() throws Exception {
        /*String newNotification = "{\n" +
                "    \"content\": \"\",\n" +
                "    \"userID\": 1,\n" +
                "    \"read\": false,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "    \"responseMessage\": {\n" +
                        "        \"success\": false,\n" +
                        "        \"status\": \"BAD_REQUEST\",\n" +
                        "        \"message\": \"Validation Failed\"\n" +
                        "    },\n" +
                        "    \"details\": [\n" +
                        "        \"Content can't be blank!!\",\n" +
                        "        \"Content must be between 2 and 150 characters!!\"\n" +
                        "    ]\n" +
                        "}"));*/
    }

    @Test
    void CreateNewNotificationSmallContent() throws Exception {
        /*String newNotification = "{\n" +
                "    \"content\": \"N\",\n" +
                "    \"userID\": 1,\n" +
                "    \"read\": false,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "    \"responseMessage\": {\n" +
                        "        \"success\": false,\n" +
                        "        \"status\": \"BAD_REQUEST\",\n" +
                        "        \"message\": \"Validation Failed\"\n" +
                        "    },\n" +
                        "    \"details\": [\n" +
                        "        \"Content must be between 2 and 150 characters!!\"\n" +
                        "    ]\n" +
                        "}"));*/
    }

    @Test
    void CreateNewNotificationNoContent() throws Exception {
        /*String newNotification = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"read\": false,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content can't be blank!!\"]}"));*/
    }

    @Test
    void CreateNewNotificationEmptyNotification() throws Exception {
        /*String newNotification = "{}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content can't be blank!!\"]}"));*/
    }

    @Test
    void CreateNewNotificationOnlyUser() throws Exception {
        /*String newNotification = "{\n" +
                "    \"userID\": 1,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content can't be blank!!\"]}"));*/
    }

    @Test
    void CreateNewNotificationOnlyContent() throws Exception {
        /*String newNotification = "{\n" +
                "    \"content\": 1,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content must be between 2 and 150 characters!!\"]}"));*/
    }

    @Test
    void CreateNewNotificationReadTrue() throws Exception {
        /*String newNotification = "{\n" +
                "    \"content\": \"Notif\",\n" +
                "    \"userID\": 1,\n" +
                "    \"read\": true,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":true,\"status\":\"OK\",\"message\":\"Notification added successfully!!\"}"));*/
    }

    @Test
    void CreateNewNotificationNoRead() throws Exception {
        /*String newNotification = "{\n" +
                "    \"content\": \"Notif\",\n" +
                "    \"userID\": 1,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":true,\"status\":\"OK\",\"message\":\"Notification added successfully!!\"}"));*/
    }

    @Test
    void CreateNewNotificationOnlyValidContent() throws Exception {
        /*String newNotification = "{\n" +
                "    \"content\": 123,\n" +
                "    \"createdAt\": null\n" +
                "}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\":true,\"status\":\"OK\",\"message\":\"Notification added successfully!!\"}"));*/
    }

    @Test
    void CreateNewNotificationIsntAdded() throws Exception {
        /*String newNotification = "{}\n";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newNotification);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"status\":\"BAD_REQUEST\",\"message\":\"Validation Failed\"},\"details\":[\"Content can't be blank!!\"]}"));*/
    }

    @Test
    void GetAllUserNotificationsInJSON() throws Exception {
        /*RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/notifications/user")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));*/
    }

    @Test
    void GetAllUnreadUserNotificationsInJSON() throws Exception {
        /*RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/notifications/unread/user")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));*/
    }

    @Test
    void GetNotificationInJSON() throws Exception {
        /*Long notificationID = 3L;
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/notifications/{notificationID}", notificationID)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));*/
    }

    @Test
    void DeleteNotificationInJSON() throws Exception {
        /*Long notificationID = 2L;
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/notifications/{notificationID}", notificationID)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));*/
    }
}

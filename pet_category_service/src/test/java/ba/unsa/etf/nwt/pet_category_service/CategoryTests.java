package ba.unsa.etf.nwt.pet_category_service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
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
public class CategoryTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void AddCategoryTest() throws Exception{

        String novaCat = "{\"name\": \"prvaCat\",\"description\": \"string\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\": true, \"message\": \"Category added successfully!\",\"status\": \"OK\"}"));
    }

    @Test
    void GetAllCategoriesInJSON() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/categories")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void GetCategoryByIDTest() throws Exception{

        Long id = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/{id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}"));
    }

    @Test
    void GetCategoryByNameTest() throws Exception{

        String categoryName = "Cat";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/byName?name={categoryName}", categoryName)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\":\"Cat\",\"description\":\"Cats, also called domestic cats (Felis catus), are small, carnivorous (meat-eating) mammals, of the family Felidae.\"}"));
    }

    @Test
    void UpdateCategoryTest() throws Exception{

        Long id = 3L;

        String novaCat = "{\"name\": \"novaCat\",\"description\": \"string\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\": \"novaCat\",\"description\": \"string\"}"));
    }

    @Test
    void DeleteCategoryTest() throws Exception{

        Long categoryID = 4L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/category?id={categoryID}", categoryID)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"success\": true, \"message\": \"Category successfully deleted!\",\"status\": \"OK\"}"));
    }

    //NOT FOUND test
    @Test
    void GetCategoryByIDNotFound() throws Exception{

        Long id = 50L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/{id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"responseMessage\": {\n" +
                        "    \"success\": false,\n" +
                        "    \"message\": \"Exception for NOT_FOUND was thrown\",\n" +
                        "    \"status\": \"NOT_FOUND\"\n" +
                        "  },\n" +
                        "  \"details\": [\n" +
                        "    \"No category with ID 50\"\n" +
                        "  ]\n" +
                        "}\n"));
    }

    @Test
    void GetCategoryByNameNotFound() throws Exception{

        String name = "abcd";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/byName?name={name}", name)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"responseMessage\": {\n" +
                        "    \"success\": false,\n" +
                        "    \"message\": \"Exception for NOT_FOUND was thrown\",\n" +
                        "    \"status\": \"NOT_FOUND\"\n" +
                        "  },\n" +
                        "  \"details\": [\n" +
                        "    \"No category with name abcd\"\n" +
                        "  ]\n" +
                        "}"));
    }

    @Test
    void DeleteCategoryNotFound() throws Exception{

        Long id = 50L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/category?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"responseMessage\": {\n" +
                        "    \"success\": false,\n" +
                        "    \"message\": \"Exception for NOT_FOUND was thrown\",\n" +
                        "    \"status\": \"NOT_FOUND\"\n" +
                        "  },\n" +
                        "  \"details\": [\n" +
                        "    \"No category with ID 50\"\n" +
                        "  ]\n" +
                        "}"));
    }

    @Test
    void UpdateCategoryNotFound() throws Exception{

        Long id = 50L;

        String novaCat = "{\"name\": \"novaCat\",\"description\": \"string\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"responseMessage\": {\n" +
                        "    \"success\": false,\n" +
                        "    \"message\": \"Exception for NOT_FOUND was thrown\",\n" +
                        "    \"status\": \"NOT_FOUND\"\n" +
                        "  },\n" +
                        "  \"details\": [\n" +
                        "    \"No category with ID 50\"\n" +
                        "  ]\n" +
                        "}"));
    }

    //BAD REQUEST tests
    @Test
    void UpdateCategoryBadRequest1() throws Exception{

        Long id = 1L;

        String novaCat = "{\"name\": \"\",\"description\": \"\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category name can't be blank!\",\"Category name must be between 2 and 50 characters!\",\"Category description can't be blank!\"]}"));
    }
    @Test
    void UpdateCategoryBadRequest2() throws Exception{

        Long id = 1L;

        String novaCat = "{\"name\": \"g\",\"description\": \"fhghjhn\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category name must be between 2 and 50 characters!\"]}"));
    }

    @Test
    void UpdateCategoryBadRequest3() throws Exception{

        Long id = 1L;

        String novaCat = "{\"name\": \"gghj\",\"description\": \"\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category description can't be blank!\"]}"));
    }

    @Test
    void UpdateCategoryBadRequest4() throws Exception{

        Long id = 2L;

        String novaCat = "{\"name\": \"Bird\",\"description\": \"dfhj\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/category/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for wrong input was thrown\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category with that name already exists!\"]}"));
    }

    @Test
    void AddCategoryBadRequest1() throws Exception{

        String novaCat = "{\"name\": \"\",\"description\": \"\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category name can't be blank!\",\"Category name must be between 2 and 50 characters!\",\"Category description can't be blank!\"]}"));
    }

    @Test
    void AddCategoryBadRequest2() throws Exception{

        String novaCat = "{\"name\": \"string\",\"description\": \"\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category description can't be blank!\"]}"));
    }

    @Test
    void AddCategoryBadRequest3() throws Exception{

        String novaCat = "{\"name\": \"\",\"description\": \"string\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category name must be between 2 and 50 characters!\",\"Category name can't be blank!\"]}"));
    }

    @Test
    void AddCategoryBadRequest4() throws Exception{

        String novaCat = "{\"name\": \"Dog\",\"description\": \"string\"}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaCat);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for wrong input was thrown\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category with name Dog already exists!\"]}"));
    }

}

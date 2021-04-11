package ba.unsa.etf.nwt.pet_category_service;

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
public class RaseTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void GetAllRasesInJSON() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rases")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void GetRasesInCategoryTest() throws Exception{

        Long id = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rases/inCategory?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\":\"American bulldog\",\"description\":\"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\"category\":{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}},{\"name\":\"novaRasaa\",\"description\":\"nova\",\"category\":{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}}]"));
    }

    @Test
    void GetRaseByIDTest() throws Exception{

        Long id = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rase/{id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"name\": \"American bulldog\",\"description\": \"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\"category\": {\"name\": \"Dog\",\"description\": \"Dogs are domesticated mammals, not natural wild animals. \"}}"));
    }

    @Test
    void GetRaseByNameTest() throws Exception{

        String raseName = "Goldfish";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rase/byName?name={raseName}", raseName)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"name\": \"Goldfish\",\n" +
                        "  \"description\": \"Another cold-water fish, goldfish belong to the carp family. Because they enjoy cool water temperatures, keep goldfish in a separate tank from warm water fish.\",\n" +
                        "  \"category\": {\"name\": \"novaCat\",\"description\": \"string\"}}"));
    }

    @Test
    void AddRaseTest() throws Exception{

        String novaRasa = "{\n" +
                "  \"name\": \"novaRasa\",\n" +
                "  \"description\": \"novaRasa\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"Rase successfully added!!\",\n" +
                        "  \"status\": \"OK\"\n" +
                        "}"));
    }

    @Test
    void DeleteRaseTest() throws Exception{

        Long raseID = 3L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/rase?id={raseID}", raseID)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"Rase successfully deleted\",\n" +
                        "  \"status\": \"OK\"\n" +
                        "}\n"));
    }


    @Test
    void UpdateRaseTest() throws Exception{

        Long id = 2L;

        String novaRase = "{\"name\": \"novaRasaa\",\"description\": \"nova\",\"category_id\": 1}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rase/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRase);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"name\": \"novaRasaa\",\n" +
                        "  \"description\": \"nova\",\n" +
                        "  \"category\": {\n" +
                        "    \"name\": \"Dog\",\n" +
                        "    \"description\": \"Dogs are domesticated mammals, not natural wild animals. \"\n" +
                        "  }\n" +
                        "}"));
    }

    //NOT FOUND tests

    @Test
    void GetRaseByIDNotFound() throws Exception{

        Long id = 50L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rase/{id}", id)
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
                        "    \"No rase with ID 50\"\n" +
                        "  ]\n" +
                        "}\n"));
    }

    @Test
    void GetRasesInCategoryNotFound() throws Exception{

        Long id = 50L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rases/inCategory?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    void GetRaseByNameNotFound() throws Exception{

        String name = "abcd";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/rase/byName?name={name}", name)
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
                        "    \"No rase with name abcd\"\n" +
                        "  ]\n" +
                        "}\n"));
    }

    @Test
    void DeleteRaseNotFound() throws Exception{

        Long id = 50L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/rase?id={id}", id)
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
                        "    \"No rase with ID 50\"\n" +
                        "  ]\n" +
                        "}"));
    }

    @Test
    void UpdateRaseNotFound1() throws Exception{

        Long id = 50L;

        String novaRase = "{\"name\": \"novaRasaa\",\"description\": \"nova\",\"category_id\": 1}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rase/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRase);
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
                        "    \"No rase with ID 50\"\n" +
                        "  ]\n" +
                        "}"));
    }

    @Test
    void UpdateRaseNotFound2() throws Exception{

        Long id = 1L;

        String novaRase = "{\"name\": \"gdfgd\",\"description\": \"nova\",\"category_id\": 50}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rase/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRase);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for NOT_FOUND was thrown\",\"status\":\"NOT_FOUND\"},\"details\":[\"No category with ID 50\"]}"));
    }


    //BAD REQUEST tests

    @Test
    void AddRaseBadRequest1() throws Exception{

        String novaRasa = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase name must be between 2 and 50 characters!\",\"Rase name can't be blank!\",\"Rase description can't be blank!\"]}"));
    }

    @Test
    void AddRaseBadRequest2() throws Exception{

        String novaRasa = "{\n" +
                "  \"name\": \"g\",\n" +
                "  \"description\": \"\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase name must be between 2 and 50 characters!\",\"Rase description can't be blank!\"]}"));
    }

    @Test
    void AddRaseBadRequest3() throws Exception{

        String novaRasa = "{\n" +
                "  \"name\": \"gfsdg\",\n" +
                "  \"description\": \"\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase description can't be blank!\"]}"));
    }

    @Test
    void AddRaseBadRequest4() throws Exception{

        String novaRasa = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"gfjdg\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase name can't be blank!\",\"Rase name must be between 2 and 50 characters!\"]}"));
    }

    @Test
    void AddRaseBadRequest5() throws Exception{

        String novaRasa = "{\n" +
                "  \"name\": \"Goldfish\",\n" +
                "  \"description\": \"gfjdg\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/rase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for wrong input was thrown\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase with that name already exists!\"]}"));
    }

    @Test
    void UpdateRaseBadRequest1() throws Exception{

        Long id = 5L;

        String novaRasa = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rase/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase name can't be blank!\",\"Rase description can't be blank!\",\"Rase name must be between 2 and 50 characters!\"]}"));
    }

    @Test
    void UpdateRaseBadRequest2() throws Exception{

        Long id = 5L;

        String novaRasa = "{\n" +
                "  \"name\": \"g\",\n" +
                "  \"description\": \"\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rase/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase name must be between 2 and 50 characters!\",\"Rase description can't be blank!\"]}"));
    }

    @Test
    void UpdateRaseBadRequest3() throws Exception{

        Long id = 5L;

        String novaRasa = "{\n" +
                "  \"name\": \"gvgss\",\n" +
                "  \"description\": \"\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rase/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase description can't be blank!\"]}"));
    }

    @Test
    void UpdateRaseBadRequest4() throws Exception{

        Long id = 5L;

        String novaRasa = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"fdsngj\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rase/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase name must be between 2 and 50 characters!\",\"Rase name can't be blank!\"]}"));
    }

    @Test
    void UpdateRaseBadRequest5() throws Exception{

        Long id = 5L;

        String novaRasa = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"fdsngj\",\n" +
                "  \"category_id\": 1\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/rase/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Rase name must be between 2 and 50 characters!\",\"Rase name can't be blank!\"]}"));
    }


}

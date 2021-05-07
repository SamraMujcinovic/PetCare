package ba.unsa.etf.nwt.pet_category_service;

import ba.unsa.etf.nwt.pet_category_service.service.CommunicationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class PetTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CommunicationsService communicationsService;

    public String getToken(){
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String input = "{\n" +
                    "  \"password\": \"Password123!\",\n" +
                    "  \"usernameOrEmail\": \"alakovic1\"\n" +
                    "}";

            HttpEntity<String> httpEntity = new HttpEntity<>(input, headers);

            final String route = communicationsService.getUri("user_service") + "/api/auth/login/token";
            URI uri = new URI(route);

            return restTemplate.postForObject(uri,
                    httpEntity, String.class);

        } catch (Exception e){
            System.out.println("Can't connect to user_service");
        }
        return null;
    }

    @Test
    void GetAllPetsInJSON() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void GetPetByIDTest() throws Exception{

        Long id = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/{id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"name\": \"Rex\",\n" +
                        "  \"location\": \"Sarajevo\",\n" +
                        "  \"image\": \"image1\",\n" +
                        "  \"description\": \"\",\n" +
                        "  \"age\": 2,\n" +
                        "  \"adopted\": false,\n" +
                        "  \"rase\": {\n" +
                        "    \"name\": \"American bulldog\",\n" +
                        "    \"description\": \"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\n" +
                        "    \"category\": {\n" +
                        "      \"name\": \"Dog\",\n" +
                        "      \"description\": \"Dogs are domesticated mammals, not natural wild animals. \"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"));
    }

    @Test
    void GetPetsInRaseTest() throws Exception{

        Long id = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets/inRase?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"name\": \"Rex\",\n" +
                        "    \"location\": \"Sarajevo\",\n" +
                        "    \"image\": \"image1\",\n" +
                        "    \"description\": \"\",\n" +
                        "    \"age\": 2,\n" +
                        "    \"adopted\": false,\n" +
                        "    \"rase\": {\n" +
                        "      \"name\": \"American bulldog\",\n" +
                        "      \"description\": \"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\n" +
                        "      \"category\": {\n" +
                        "        \"name\": \"Dog\",\n" +
                        "        \"description\": \"Dogs are domesticated mammals, not natural wild animals. \"\n" +
                        "      }\n" +
                        "    }\n" +
                        "  }\n" +
                        "]"));
    }

    @Test
    void GetPetsInCategoryTest() throws Exception{

        Long id = 1L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets/inCategory?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\":\"Rex\",\"location\":\"Sarajevo\",\"image\":\"image1\",\"description\":\"\",\"age\":2,\"adopted\":false,\"rase\":{\"name\":\"American bulldog\",\"description\":\"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\"category\":{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}}},{\"name\":\"noviPet\",\"location\":\"string\",\"image\":\"string\",\"description\":\"string\",\"age\":5,\"adopted\":true,\"rase\":{\"name\":\"American bulldog\",\"description\":\"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\"category\":{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}}},{\"name\":\"newPet\",\"location\":\"string\",\"image\":\"string\",\"description\":\"string\",\"age\":5,\"adopted\":true,\"rase\":{\"name\":\"American bulldog\",\"description\":\"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\"category\":{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}}}]"));
    }

    @Test
    void GetPetByNameTest() throws Exception{

        String petName = "Rex";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/byName?name={petName}", petName)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\n{\n" +
                        "  \"name\": \"Rex\",\n" +
                        "  \"location\": \"Sarajevo\",\n" +
                        "  \"image\": \"image1\",\n" +
                        "  \"description\": \"\",\n" +
                        "  \"age\": 2,\n" +
                        "  \"adopted\": false,\n" +
                        "  \"rase\": {\n" +
                        "    \"name\": \"American bulldog\",\n" +
                        "    \"description\": \"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\n" +
                        "    \"category\": {\n" +
                        "      \"name\": \"Dog\",\n" +
                        "      \"description\": \"Dogs are domesticated mammals, not natural wild animals. \"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n]"));
    }

    @Test
    void GetPetByNameContainsTest() throws Exception{

        String substring = "ex";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets/name/contains?substring={substring}", substring)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[\n{\n" +
                        "  \"name\": \"Rex\",\n" +
                        "  \"location\": \"Sarajevo\",\n" +
                        "  \"image\": \"image1\",\n" +
                        "  \"description\": \"\",\n" +
                        "  \"age\": 2,\n" +
                        "  \"adopted\": false,\n" +
                        "  \"rase\": {\n" +
                        "    \"name\": \"American bulldog\",\n" +
                        "    \"description\": \"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\n" +
                        "    \"category\": {\n" +
                        "      \"name\": \"Dog\",\n" +
                        "      \"description\": \"Dogs are domesticated mammals, not natural wild animals. \"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}\n]"));
    }

    @Test
    void GetPetByRaseContainsTest() throws Exception{

        String substring = "dog";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets/rase/contains?substring={substring}", substring)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\":\"Rex\",\"location\":\"Sarajevo\",\"image\":\"image1\",\"description\":\"\",\"age\":2,\"adopted\":false,\"rase\":{\"name\":\"American bulldog\",\"description\":\"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\"category\":{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}}},{\"name\":\"noviPet\",\"location\":\"string\",\"image\":\"string\",\"description\":\"string\",\"age\":5,\"adopted\":true,\"rase\":{\"name\":\"American bulldog\",\"description\":\"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\"category\":{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}}},{\"name\":\"newPet\",\"location\":\"string\",\"image\":\"string\",\"description\":\"string\",\"age\":5,\"adopted\":true,\"rase\":{\"name\":\"American bulldog\",\"description\":\"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\"category\":{\"name\":\"Dog\",\"description\":\"Dogs are domesticated mammals, not natural wild animals. \"}}}]"));
    }

    @Test
    void AddPetTest() throws Exception{

        String novaRasa = "{\n" +
                "  \"name\": \"newPet\",\n" +
                "  \"location\": \"string\",\n" +
                "  \"image\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 1\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/pet")
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(novaRasa);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"Pet successfully added!!\",\n" +
                        "  \"status\": \"OK\"\n" +
                        "}"));
    }

    @Test
    void DeletePetTest() throws Exception{

        Long petID = 4L;

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/pet?id={petID}", petID)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"success\": true,\n" +
                        "  \"message\": \"Pet successfully deleted!\",\n" +
                        "  \"status\": \"OK\"\n" +
                        "}\n"));
    }

    @Test
    void UpdatePetTest() throws Exception{

        Long id = 2L;

        String noviPet = "{\n" +
                "  \"name\": \"noviPet\",\n" +
                "  \"location\": \"string\",\n" +
                "  \"image\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 1\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "  \"name\": \"noviPet\",\n" +
                        "  \"location\": \"string\",\n" +
                        "  \"image\": \"string\",\n" +
                        "  \"description\": \"string\",\n" +
                        "  \"age\": 5,\n" +
                        "  \"adopted\": true,\n" +
                        "  \"rase\": {\n" +
                        "    \"name\": \"American bulldog\",\n" +
                        "    \"description\": \"The American Bulldog is stocky and muscular, but also agile and built for chasing down stray cattle and helping with farm work.American Bulldogs are intelligent.\",\n" +
                        "    \"category\": {\n" +
                        "      \"name\": \"Dog\",\n" +
                        "      \"description\": \"Dogs are domesticated mammals, not natural wild animals. \"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}"));
    }

    //NOT FOUND tests

    @Test
    void GetPetByIDNotFound() throws Exception{

        Long id = 50L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/{id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for NOT_FOUND was thrown\",\"status\":\"NOT_FOUND\"},\"details\":[\"No pet with ID 50\"]}"));
    }

    @Test
    void GetPetsInRaseNotFound() throws Exception{

        Long id = 50L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets/inRase?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    void GetPetsInCategoryNotFound() throws Exception{

        Long id = 50L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets/inCategory?id={id}", id)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    void GetPetByNameNotFound() throws Exception{

        String name = "behwbf";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet/byName?name={name}", name)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for NOT_FOUND was thrown\",\"status\":\"NOT_FOUND\"},\"details\":[\"No pet with name behwbf\"]}"));
    }

    @Test
    void GetPetByNameContainsNotFound() throws Exception{

        String substring = "bebf";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets/name/contains?substring={substring}", substring)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    void GetPetByRaseContainsNotFound() throws Exception{

        String substring = "bebf";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pets/rase/contains?substring={substring}", substring)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    void DeletePetNotFound() throws Exception{

        Long id = 50L;

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/pet?id={id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for NOT_FOUND was thrown\",\"status\":\"NOT_FOUND\"},\"details\":[\"No pet with ID 50\"]}"));
    }

    @Test
    void UpdatePetNotFound() throws Exception{

        Long id = 50L;

        String noviPet = "{\n" +
                "  \"name\": \"noviPet\",\n" +
                "  \"location\": \"string\",\n" +
                "  \"image\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for NOT_FOUND was thrown\",\"status\":\"NOT_FOUND\"},\"details\":[\"No pet with ID 50\"]}"));
    }

    @Test
    void UpdatePetRaseNotFound() throws Exception{

        Long id = 1L;

        String noviPet = "{\n" +
                "  \"name\": \"noviPet\",\n" +
                "  \"location\": \"string\",\n" +
                "  \"image\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 70\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Exception for NOT_FOUND was thrown\",\"status\":\"NOT_FOUND\"},\"details\":[\"No rase with ID 70\"]}"));
    }

    //BAD REQUEST tests

    @Test
    void UpdatePetBadRequest1() throws Exception{

        Long id = 10L;

        String noviPet = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"location\": \"string\",\n" +
                "  \"image\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Pet name must be between 2 and 50 characters!\",\"Pet name can't be blank!\"]}"));
    }

    @Test
    void UpdatePetBadRequest2() throws Exception{

        Long id = 10L;

        String noviPet = "{\n" +
                "  \"name\": \"g\",\n" +
                "  \"location\": \"string\",\n" +
                "  \"image\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Pet name must be between 2 and 50 characters!\"]}"));
    }

    @Test
    void UpdatePetBadRequest3() throws Exception{

        Long id = 10L;

        String noviPet = "{\n" +
                "  \"name\": \"gfghb\",\n" +
                "  \"location\": \"\",\n" +
                "  \"image\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Pet location can't be blank!\"]}"));
    }

    @Test
    void UpdatePetBadRequest4() throws Exception{

        Long id = 10L;

        String noviPet = "{\n" +
                "  \"name\": \"gfghb\",\n" +
                "  \"location\": \"\",\n" +
                "  \"image\": \"\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Pet image can't be blank!\",\"Pet location can't be blank!\"]}"));
    }
/*
    @Test
    void UpdatePetBadRequest5() throws Exception{

        Long id = 10L;

        String noviPet = "{\n" +
                "  \"name\": \"gfghb\",\n" +
                "  \"location\": \"ghb\",\n" +
                "  \"image\": \"gvb\",\n" +
                "  \"description\": \"\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Category name can't be blank!\",\"Category name must be between 2 and 50 characters!\",\"Category description can't be blank!\"]}"));
    }*/

    @Test
    void UpdatePetBadRequest6() throws Exception{

        Long id = 10L;

        String noviPet = "{\n" +
                "  \"name\": \"gfghb\",\n" +
                "  \"location\": \"ghb\",\n" +
                "  \"image\": \"gvb\",\n" +
                "  \"description\": \"gbjn\",\n" +
                "  \"age\": null,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Pet age can't be blank!\"]}"));
    }

    @Test
    void UpdatePetBadRequest7() throws Exception{

        Long id = 10L;

        String noviPet = "{\n" +
                "  \"name\": \"gfghb\",\n" +
                "  \"location\": \"ghb\",\n" +
                "  \"image\": \"gvb\",\n" +
                "  \"description\": \"gbjn\",\n" +
                "  \"age\": 200,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        String token = "Bearer " + getToken();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(noviPet);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"responseMessage\":{\"success\":false,\"message\":\"Validation Failed\",\"status\":\"BAD_REQUEST\"},\"details\":[\"Pet can't be older than 100 years!\"]}"));
    }



}

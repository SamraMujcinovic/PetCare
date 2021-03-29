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
public class PetTests {

    @Autowired
    private MockMvc mockMvc;

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

        Long id = 10L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pet?id={id}", id)
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

        Long id = 5L;

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
                "  \"rase_id\": 5\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/pet")
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

        Long petID = 13L;

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/pet?id={petID}", petID)
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

        Long id = 11L;

        String noviPet = "{\n" +
                "  \"name\": \"noviPet\",\n" +
                "  \"location\": \"string\",\n" +
                "  \"image\": \"string\",\n" +
                "  \"description\": \"string\",\n" +
                "  \"age\": 5,\n" +
                "  \"adopted\": true,\n" +
                "  \"rase_id\": 5\n" +
                "}";

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/pet/update/{id}", id)
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

}

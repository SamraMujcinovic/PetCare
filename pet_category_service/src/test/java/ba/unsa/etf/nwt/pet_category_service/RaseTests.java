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

        Long id = 5L;

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

        Long raseID = 7L;

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

        Long id = 6L;

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
}

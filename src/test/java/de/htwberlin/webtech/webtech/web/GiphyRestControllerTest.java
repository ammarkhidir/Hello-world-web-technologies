package de.htwberlin.webtech.webtech.web;

import de.htwberlin.webtech.webtech.service.GiphyService;
import de.htwberlin.webtech.webtech.web.api.FavouriteGiphy;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GiphyRestController.class)
public class GiphyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GiphyService giphyService;

    @Test
    @DisplayName("should return giphy from giphy service")
    void should_return_found_giphy_from_giphy_service() throws Exception {
        // given
        var persons = List.of(
                new FavouriteGiphy(1, "Title1", "https://example1.com"),
                new FavouriteGiphy(2, "Title2", "https://example2.com")
        );
        doReturn(persons).when(giphyService).findAll();

        // when
        mockMvc.perform(get("/api/v1/giphys"))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Title1"))
                .andExpect(jsonPath("$[0].link").value("https://example1.com"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Title2"))
                .andExpect(jsonPath("$[1].link").value("https://example2.com"));
    }
    @Test
    @DisplayName("should return 404 if giphy is not found")
    void should_return_404_if_giphy_is_not_found() throws Exception {
        // given
        doReturn(null).when(giphyService).findById(anyLong());

        // when
        mockMvc.perform(get("/api/v1/giphys/123"))
                // then
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("should return 201 http status and Location header when creating a person")
    void should_return_201_http_status_and_location_header_when_creating_a_giphy() throws Exception {
        // given
        String giphyToCreateAsJson = "{\"title\": \"Example\", \"link\":\"exampleURL\"}";
        var giphy = new FavouriteGiphy(123, null, null);
        doReturn(giphy).when(giphyService).create(any());

        // when
        mockMvc.perform(
                        post("/api/v1/giphys")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(giphyToCreateAsJson)
                )
                // then
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", Matchers.equalTo(("/api/v1/giphys" + giphy.getId()))));
//            .andExpect(header().string("Location", Matchers.containsString(Long.toString(person.getId()))));

    }

    @Test
    @DisplayName("should validate create person request")
    void should_validate_create_person_request() throws Exception {
        // given
        String giphyToCreateAsJson = "{\"title\": \"a\", \"link\":\"\"}";

        // when
        mockMvc.perform(
                        post("/api/v1/giphys")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(giphyToCreateAsJson)
                )
                // then
                .andExpect(status().isBadRequest());
    }
}

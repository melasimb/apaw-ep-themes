package es.upm.miw.apaw_ep_themes.api_controllers;

import es.upm.miw.apaw_ep_themes.ApiTestConfig;
import es.upm.miw.apaw_ep_themes.dtos.SuggestionDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
class SuggestionResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        SuggestionDto suggestionDto = this.webTestClient
                .post().uri(SuggestionResource.SUGGESTIONS)
                .body(BodyInserters.fromObject(new SuggestionDto(false, "Sugerencia... ")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SuggestionDto.class).returnResult().getResponseBody();
        assertNotNull(suggestionDto);
        assertFalse(suggestionDto.getNegative());
        assertEquals("Sugerencia... ", suggestionDto.getDescription());
    }

    @Test
    void testCreateSuggestionException() {
        SuggestionDto suggestionDto = new SuggestionDto(false, null);
        this.webTestClient
                .post().uri(SuggestionResource.SUGGESTIONS)
                .body(BodyInserters.fromObject(suggestionDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

}

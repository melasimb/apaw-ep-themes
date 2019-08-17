package es.upm.miw.apaw_ep_themes.theme_resource;

import es.upm.miw.apaw_ep_themes.ApiTestConfig;
import es.upm.miw.apaw_ep_themes.user_resource.UserBasicDto;
import es.upm.miw.apaw_ep_themes.user_resource.UserCreationDto;
import es.upm.miw.apaw_ep_themes.user_resource.UserResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTestConfig
class ThemeResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    String createTheme(String reference) {
        UserCreationDto userCreationDto =
                new UserCreationDto("nick" + reference, "nick" + reference + "@gmail.com", "country", "city", null);
        String userId = this.webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromObject(userCreationDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserBasicDto.class)
                .returnResult().getResponseBody().getId();
        return this.webTestClient
                .post().uri(ThemeResource.THEMES)
                .body(BodyInserters.fromObject(new ThemeCreationDto(reference, userId)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ThemeBasicDto.class)
                .returnResult().getResponseBody().getId();
    }

    @Test
    void testCreate() {
        this.createTheme("theme-1");
    }

    @Test
    void testCreateUserIdException() {
        ThemeCreationDto themeCreationDto =
                new ThemeCreationDto("resource_theme-2", "no");
        this.webTestClient
                .post().uri(ThemeResource.THEMES)
                .body(BodyInserters.fromObject(themeCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateThemeException() {
        ThemeCreationDto themeCreationDto =
                new ThemeCreationDto("", "no");
        this.webTestClient
                .post().uri(ThemeResource.THEMES)
                .body(BodyInserters.fromObject(themeCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateVotesThemeIdException() {
        this.webTestClient
                .post().uri(ThemeResource.THEMES + ThemeResource.ID_ID + ThemeResource.VOTES, "n0")
                .body(BodyInserters.fromObject(new VoteDto(2)))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateVotesVoteException() {
        this.webTestClient
                .post().uri(ThemeResource.THEMES + ThemeResource.ID_ID + ThemeResource.VOTES, "n0")
                .body(BodyInserters.fromObject(new VoteDto()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateVotes() {
        String themeId = this.createTheme("theme-2");
        this.webTestClient
                .post().uri(ThemeResource.THEMES + ThemeResource.ID_ID + ThemeResource.VOTES, themeId)
                .body(BodyInserters.fromObject(new VoteDto(2)))
                .exchange()
                .expectStatus().isOk();
        this.webTestClient
                .post().uri(ThemeResource.THEMES + ThemeResource.ID_ID + ThemeResource.VOTES, themeId)
                .body(BodyInserters.fromObject(new VoteDto(3)))
                .exchange()
                .expectStatus().isOk();
        this.webTestClient
                .post().uri(ThemeResource.THEMES + ThemeResource.ID_ID + ThemeResource.VOTES, themeId)
                .body(BodyInserters.fromObject(new VoteDto(4)))
                .exchange()
                .expectStatus().isOk();
        Double average = this.webTestClient
                .get().uri(ThemeResource.THEMES + ThemeResource.ID_ID + ThemeResource.AVERAGE, themeId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AverageDto.class)
                .returnResult().getResponseBody().getAverage();
        assertEquals(3, average, 10e-5);

    }

}

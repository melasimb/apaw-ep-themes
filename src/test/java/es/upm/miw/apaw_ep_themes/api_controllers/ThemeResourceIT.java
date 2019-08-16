package es.upm.miw.apaw_ep_themes.api_controllers;

import es.upm.miw.apaw_ep_themes.ApiTestConfig;
import es.upm.miw.apaw_ep_themes.dtos.ThemeBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.ThemeCreationDto;
import es.upm.miw.apaw_ep_themes.dtos.UserBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.UserCreationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@ApiTestConfig
public class ThemeResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    private String themeId;

    @Test
    void testCreate() {
        UserCreationDto userCreationDto =
                new UserCreationDto("nick-t1", "email", "country", "city", null);
        String userId = this.webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromObject(userCreationDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserBasicDto.class)
                .returnResult().getResponseBody().getId();
        ThemeCreationDto themeCreationDto =
                new ThemeCreationDto("resource_theme-1", userId);
        this.themeId = this.webTestClient
                .post().uri(ThemeResource.THEMES)
                .body(BodyInserters.fromObject(themeCreationDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ThemeBasicDto.class)
                .returnResult().getResponseBody().getId();
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
}

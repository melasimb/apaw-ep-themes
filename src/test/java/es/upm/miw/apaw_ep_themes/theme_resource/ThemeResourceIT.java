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

}

package es.upm.miw.apaw_ep_themes.user_resource;

import es.upm.miw.apaw_ep_themes.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTestConfig
class UserResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        UserBasicDto userBasicDto = createUser("nick");
        assertEquals("nick", userBasicDto.getNick());
    }

    UserBasicDto createUser(String nick) {
        UserCreationDto userCreationDto =
                new UserCreationDto(nick, nick + "@gmail.com", "country", "city", null);
        return this.webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromObject(userCreationDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserBasicDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreateUserException() {
        UserCreationDto userCreationDto =
                new UserCreationDto("nick-2", null, "country", null, "street");
        this.webTestClient
                .post().uri(UserResource.USERS)
                .body(BodyInserters.fromObject(userCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testReadUserNick() {
        String id = createUser("nick-3").getId();
        UserBasicDto userBasicDto = this.webTestClient
                .get().uri(UserResource.USERS + UserResource.ID_ID + UserResource.NICK, id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserBasicDto.class)
                .returnResult().getResponseBody();
        assertEquals(id, userBasicDto.getId());
        assertEquals("nick-3", userBasicDto.getNick());
    }

    @Test
    void testReadUserNickException() {
        this.webTestClient
                .get().uri(UserResource.USERS + UserResource.ID_ID + UserResource.NICK, "no")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

}

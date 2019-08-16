package es.upm.miw.apaw_ep_themes.api_controllers;

import es.upm.miw.apaw_ep_themes.ApiTestConfig;
import es.upm.miw.apaw_ep_themes.dtos.UserBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.UserCreationDto;
import es.upm.miw.apaw_ep_themes.dtos.UserPatchDto;
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

    @Test
    void testUserUpdateUserPatchDtoException() {
        String id = createUser("nick-4").getId();
        this.webTestClient
                .patch().uri(UserResource.USERS + UserResource.ID_ID, id)
                .body(BodyInserters.fromObject(new UserPatchDto()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserUpdateIdException() {
        this.webTestClient
                .patch().uri(UserResource.USERS + UserResource.ID_ID, "no")
                .body(BodyInserters.fromObject(new UserPatchDto("email", "other")))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUserUpdate() {
        String id = createUser("nick-5").getId();
        this.webTestClient
                .patch().uri(UserResource.USERS + UserResource.ID_ID, id)
                .body(BodyInserters.fromObject(new UserPatchDto("email", "other@gmail.com")))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testPutNick() {
        String id = createUser("nick-6").getId();
        UserBasicDto userBasicDto = new UserBasicDto();
        userBasicDto.setNick("newNick");
        this.webTestClient
                .put().uri(UserResource.USERS + UserResource.ID_ID + UserResource.NICK, id)
                .body(BodyInserters.fromObject(userBasicDto))
                .exchange()
                .expectStatus().isOk();
        userBasicDto = this.webTestClient
                .get().uri(UserResource.USERS + UserResource.ID_ID + UserResource.NICK, id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserBasicDto.class)
                .returnResult().getResponseBody();
        assertEquals(id, userBasicDto.getId());
        assertEquals("newNick", userBasicDto.getNick());
    }

    @Test
    void testPutNickNotFoundException() {
        String id = createUser("nick-7").getId();
        this.webTestClient
                .put().uri(UserResource.USERS + UserResource.ID_ID + UserResource.NICK, id)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutNickBadRequestException() {
        this.webTestClient
                .put().uri(UserResource.USERS + UserResource.ID_ID + UserResource.NICK, "no")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }


}

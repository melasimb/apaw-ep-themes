package es.upm.miw.apaw_ep_themes.api_controllers;

import es.upm.miw.apaw_ep_themes.business_controllers.UserBusinessController;
import es.upm.miw.apaw_ep_themes.dtos.UserBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.UserCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {

    public static final String USERS = "/users";
    static final String ID_ID = "/{id}";
    static final String NICK = "/nick";

    private UserBusinessController userBusinessController;

    @Autowired
    public UserResource(UserBusinessController userBusinessController) {
        this.userBusinessController = userBusinessController;
    }

    @PostMapping
    public UserBasicDto create(@RequestBody UserCreationDto userCreationDto) {
        userCreationDto.validate();
        return this.userBusinessController.create(userCreationDto);
    }

}

package es.upm.miw.apaw_ep_themes.user_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {

    static final String USERS = "/users";
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

    @GetMapping(value = ID_ID + NICK)
    public UserBasicDto readNick(@PathVariable String id) {
        return this.userBusinessController.readNick(id);
    }

}

package es.upm.miw.apaw_ep_themes.api_controllers;

import es.upm.miw.apaw_ep_themes.business_controllers.UserBusinessController;
import es.upm.miw.apaw_ep_themes.dtos.UserBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.UserCreationDto;
import es.upm.miw.apaw_ep_themes.dtos.UserPatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = ID_ID + NICK)
    public UserBasicDto readNick(@PathVariable String id) {
        return this.userBusinessController.readNick(id);
    }

    @PatchMapping(value = ID_ID)
    public void patch(@PathVariable String id, @RequestBody UserPatchDto userPatchDto) {
        userPatchDto.validate();
        this.userBusinessController.patch(id, userPatchDto);
    }

    @PutMapping(value = ID_ID + NICK)
    public void updateNick(@PathVariable String id, @RequestBody UserBasicDto userBasicDto) {
        userBasicDto.validateNick();
        this.userBusinessController.updateNick(id, userBasicDto.getNick());
    }


}

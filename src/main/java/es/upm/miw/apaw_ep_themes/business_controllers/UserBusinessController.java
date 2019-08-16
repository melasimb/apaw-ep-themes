package es.upm.miw.apaw_ep_themes.business_controllers;

import es.upm.miw.apaw_ep_themes.daos.UserDao;
import es.upm.miw.apaw_ep_themes.documents.Address;
import es.upm.miw.apaw_ep_themes.documents.User;
import es.upm.miw.apaw_ep_themes.dtos.UserBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.UserCreationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserBusinessController {

    private UserDao userDao;

    @Autowired
    public UserBusinessController(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserBasicDto create(UserCreationDto userCreationDto) {
        User user = new User(userCreationDto.getNick(), userCreationDto.getEmail(),
                new Address(userCreationDto.getCountry(), userCreationDto.getCity(), userCreationDto.getStreet()));
        this.userDao.save(user);
        return new UserBasicDto(user);
    }

}

package es.upm.miw.apaw_ep_themes.business_controllers;

import es.upm.miw.apaw_ep_themes.daos.UserDao;
import es.upm.miw.apaw_ep_themes.documents.Address;
import es.upm.miw.apaw_ep_themes.documents.User;
import es.upm.miw.apaw_ep_themes.dtos.UserBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.UserCreationDto;
import es.upm.miw.apaw_ep_themes.dtos.UserPatchDto;
import es.upm.miw.apaw_ep_themes.exceptions.BadRequestException;
import es.upm.miw.apaw_ep_themes.exceptions.NotFoundException;
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

    public UserBasicDto readNick(String id) {
        return new UserBasicDto(this.findUserByIdAssured(id));
    }

    private User findUserByIdAssured(String id) {
        return this.userDao.findById(id).orElseThrow(() -> new NotFoundException("User id: " + id));
    }

    public void patch(String id, UserPatchDto userPatchDto) {
        User user = this.findUserByIdAssured(id);
        switch (userPatchDto.getPath()) {
            case "email":
                user.setEmail(userPatchDto.getNewValue());
                break;
            case "country":
                user.getAddress().setCountry(userPatchDto.getNewValue());
                break;
            case "city":
                user.getAddress().setCity(userPatchDto.getNewValue());
                break;
            default:
                throw new BadRequestException("UserPatchDto is invalid");
        }
        this.userDao.save(user);
    }

    public void updateNick(String id, String nick) {
        User user = this.findUserByIdAssured(id);
        user.setNick(nick);
        this.userDao.save(user);
    }

}

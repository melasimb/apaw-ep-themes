package es.upm.miw.apaw_ep_themes.theme_resource;

import es.upm.miw.apaw_ep_themes.exceptions.NotFoundException;
import es.upm.miw.apaw_ep_themes.user_data.User;
import es.upm.miw.apaw_ep_themes.user_data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ThemeBusinessController {

    private ThemeDao themeDao;

    private UserDao userDao;

    @Autowired
    public ThemeBusinessController(ThemeDao themeDao, UserDao userDao) {
        this.themeDao = themeDao;
        this.userDao = userDao;
    }

    public ThemeBasicDto create(ThemeCreationDto themeCreationDto) {
        User user = this.userDao.findById(themeCreationDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User id: " + themeCreationDto.getUserId()));
        Theme theme = new Theme(themeCreationDto.getReference(), user);
        this.themeDao.save(theme);
        return new ThemeBasicDto(theme);
    }

}

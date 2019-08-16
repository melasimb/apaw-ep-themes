package es.upm.miw.apaw_ep_themes.business_controllers;

import es.upm.miw.apaw_ep_themes.daos.ThemeDao;
import es.upm.miw.apaw_ep_themes.daos.UserDao;
import es.upm.miw.apaw_ep_themes.documents.Theme;
import es.upm.miw.apaw_ep_themes.documents.User;
import es.upm.miw.apaw_ep_themes.documents.Vote;
import es.upm.miw.apaw_ep_themes.dtos.AverageDto;
import es.upm.miw.apaw_ep_themes.dtos.ThemeBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.ThemeCreationDto;
import es.upm.miw.apaw_ep_themes.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

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

    public void createVote(String id, Integer vote) {
        Theme theme = this.findThemeByIdAssured(id);
        theme.getVotes().add(new Vote(vote));
        this.themeDao.save(theme);
    }

    private Theme findThemeByIdAssured(String id) {
        return this.themeDao.findById(id).orElseThrow(() -> new NotFoundException("Theme id: " + id));
    }

    public AverageDto processAverage(String id) {
        Theme theme = this.findThemeByIdAssured(id);
        return new AverageDto(this.average(theme));
    }

    private Double average(Theme theme) {
        return theme.getVotes().stream().mapToDouble(Vote::getValue).average().orElse(Double.NaN);
    }

    public List<ThemeBasicDto> findByAverageGreaterThanEqual(Double value) {
        return this.themeDao.findAll().stream()
                .filter(theme -> this.average(theme) >= value)
                .map(ThemeBasicDto::new)
                .collect(Collectors.toList());
    }

}

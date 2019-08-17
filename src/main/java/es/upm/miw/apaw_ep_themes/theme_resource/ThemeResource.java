package es.upm.miw.apaw_ep_themes.theme_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ThemeResource.THEMES)
public class ThemeResource {

    static final String THEMES = "/themes";
    static final String ID_ID = "/{id}";
    static final String VOTES = "/votes";

    private ThemeBusinessController themeBusinessController;

    @Autowired
    public ThemeResource(ThemeBusinessController themeBusinessController) {
        this.themeBusinessController = themeBusinessController;
    }

    @PostMapping
    public ThemeBasicDto create(@RequestBody ThemeCreationDto themeCreationDto) {
        themeCreationDto.validate();
        return this.themeBusinessController.create(themeCreationDto);
    }

    @PostMapping(value = ID_ID + VOTES)
    public void createVote(@PathVariable String id, @RequestBody VoteDto voteDto) {
        voteDto.validate();
        this.themeBusinessController.createVote(id, voteDto.getVote());
    }
}

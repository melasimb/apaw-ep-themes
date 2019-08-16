package es.upm.miw.apaw_ep_themes.api_controllers;

import es.upm.miw.apaw_ep_themes.business_controllers.ThemeBusinessController;
import es.upm.miw.apaw_ep_themes.dtos.AverageDto;
import es.upm.miw.apaw_ep_themes.dtos.ThemeBasicDto;
import es.upm.miw.apaw_ep_themes.dtos.ThemeCreationDto;
import es.upm.miw.apaw_ep_themes.dtos.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ThemeResource.THEMES)
public class ThemeResource {

    static final String THEMES = "/themes";
    static final String ID_ID = "/{id}";
    static final String VOTES = "/votes";
    static final String AVERAGE = "/overage";

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

    @GetMapping(value = ID_ID + AVERAGE)
    public AverageDto readAverage(@PathVariable String id) {
        return this.themeBusinessController.processAverage(id);
    }

}

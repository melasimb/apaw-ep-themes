package es.upm.miw.apaw_ep_themes.theme_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ThemeResource.THEMES)
public class ThemeResource {

    static final String THEMES = "/themes";

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

}

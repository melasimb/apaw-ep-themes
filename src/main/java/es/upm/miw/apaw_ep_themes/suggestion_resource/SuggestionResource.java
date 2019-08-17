package es.upm.miw.apaw_ep_themes.suggestion_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(SuggestionResource.SUGGESTIONS)
public class SuggestionResource {

    static final String SUGGESTIONS = "/suggestions";

    private SuggestionBusinessController suggestionBusinessController;

    @Autowired
    public SuggestionResource(SuggestionBusinessController suggestionBusinessController) {
        this.suggestionBusinessController = suggestionBusinessController;
    }

    @PostMapping
    public SuggestionDto create(@RequestBody SuggestionDto suggestionDto) {
        suggestionDto.validate();
        return this.suggestionBusinessController.create(suggestionDto);
    }

    @GetMapping
    public List<SuggestionDto> readAll() {
        return this.suggestionBusinessController.readAll();
    }
}

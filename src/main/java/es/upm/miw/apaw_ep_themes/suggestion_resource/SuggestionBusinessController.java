package es.upm.miw.apaw_ep_themes.suggestion_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SuggestionBusinessController {

    private SuggestionDao suggestionDao;

    @Autowired
    public SuggestionBusinessController(SuggestionDao suggestionDao) {
        this.suggestionDao = suggestionDao;
    }

    public SuggestionDto create(SuggestionDto suggestionDto) {
        Suggestion suggestion = new Suggestion(suggestionDto.getNegative(), suggestionDto.getDescription());
        this.suggestionDao.save(suggestion);
        return new SuggestionDto(suggestion);
    }

}

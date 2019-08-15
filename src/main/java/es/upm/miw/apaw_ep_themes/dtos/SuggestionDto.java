package es.upm.miw.apaw_ep_themes.dtos;

import es.upm.miw.apaw_ep_themes.documents.Suggestion;
import es.upm.miw.apaw_ep_themes.exceptions.BadRequestException;

public class SuggestionDto {

    private String id;

    private Boolean negative;

    private String description;

    public SuggestionDto() {
        // empty for framework
    }

    public SuggestionDto(Boolean negative, String description) {
        this.negative = negative;
        this.description = description;
    }

    public SuggestionDto(Suggestion suggestion) {
        this.id = suggestion.getId();
        this.negative = suggestion.getNegative();
        this.description = suggestion.getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getNegative() {
        return negative;
    }

    public void setNegative(Boolean negative) {
        this.negative = negative;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void validate() {
        if (negative == null || description == null || description.isEmpty()) {
            throw new BadRequestException("Incomplete SuggestionDto. ");
        }
    }

    @Override
    public String toString() {
        return "SuggestionDto{" +
                "id='" + id + '\'' +
                ", negative=" + negative +
                ", description='" + description + '\'' +
                '}';
    }
}

package es.upm.miw.apaw_ep_themes.dtos;

import es.upm.miw.apaw_ep_themes.documents.Theme;

public class ThemeBasicDto {

    private String id;

    private String reference;

    public ThemeBasicDto() {
        // Empty for framework
    }

    public ThemeBasicDto(Theme theme) {
        this.id = theme.getId();
        this.reference = theme.getReference();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "ThemeBasicDto{" +
                "id='" + id + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}

package es.upm.miw.apaw_ep_themes.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Suggestion {

    @Id
    private String id;

    private Boolean negative;

    private String description;

    public Suggestion(Boolean negative, String description) {
        this.negative = negative;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public Boolean getNegative() {
        return negative;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "id='" + id + '\'' +
                ", negative=" + negative +
                ", description='" + description + '\'' +
                '}';
    }
}

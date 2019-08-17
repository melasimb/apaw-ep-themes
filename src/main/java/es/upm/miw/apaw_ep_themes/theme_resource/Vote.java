package es.upm.miw.apaw_ep_themes.theme_resource;

import java.time.LocalDateTime;

public class Vote {

    private Integer value;

    private LocalDateTime date;

    public Vote(Integer value) {
        this.value = value;
        this.date = LocalDateTime.now();
    }

    public Integer getValue() {
        return value;
    }

    public LocalDateTime getDate() {
        return date;
    }

}

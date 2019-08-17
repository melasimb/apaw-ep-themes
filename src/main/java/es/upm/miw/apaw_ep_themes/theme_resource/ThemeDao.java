package es.upm.miw.apaw_ep_themes.theme_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThemeDao extends MongoRepository<Theme, String> {
}

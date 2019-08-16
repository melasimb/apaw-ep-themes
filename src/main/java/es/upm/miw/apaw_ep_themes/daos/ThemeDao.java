package es.upm.miw.apaw_ep_themes.daos;

import es.upm.miw.apaw_ep_themes.documents.Theme;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThemeDao extends MongoRepository<Theme, String> {
}

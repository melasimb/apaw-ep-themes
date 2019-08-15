package es.upm.miw.apaw_ep_themes.daos;

import es.upm.miw.apaw_ep_themes.documents.Suggestion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuggestionDao extends MongoRepository<Suggestion, String> {
}

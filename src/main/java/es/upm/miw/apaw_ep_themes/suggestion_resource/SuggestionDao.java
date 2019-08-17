package es.upm.miw.apaw_ep_themes.suggestion_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuggestionDao extends MongoRepository<Suggestion, String> {
}

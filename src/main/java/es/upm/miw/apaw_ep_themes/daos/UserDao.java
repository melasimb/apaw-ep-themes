package es.upm.miw.apaw_ep_themes.daos;

import es.upm.miw.apaw_ep_themes.documents.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDao extends MongoRepository<User, String> {
}

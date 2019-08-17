package es.upm.miw.apaw_ep_themes.user_data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDao extends MongoRepository<User, String> {
}

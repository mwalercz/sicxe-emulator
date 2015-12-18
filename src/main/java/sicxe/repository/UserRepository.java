package sicxe.repository;

import sicxe.model.database.User;

import java.util.Optional;

/**
 * Created by maciek on 08.12.15.
 */
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);
}

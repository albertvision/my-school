package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByPassword(String password);
}

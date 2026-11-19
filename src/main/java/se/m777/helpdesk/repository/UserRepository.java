
package se.m777.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.m777.helpdesk.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}




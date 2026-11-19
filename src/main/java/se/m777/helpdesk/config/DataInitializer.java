
package se.m777.helpdesk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.m777.helpdesk.entity.Role;
import se.m777.helpdesk.entity.User;
import se.m777.helpdesk.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createTestUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${TEST_PASSWORD}") String password) {

        return args -> {
            createUser(userRepository, passwordEncoder, "user", password, Role.USER);
            createUser(userRepository, passwordEncoder, "user2", password, Role.USER);
            createUser(userRepository, passwordEncoder, "agent", password, Role.AGENT);
        };
    }

    private void createUser(
            UserRepository repository,
            PasswordEncoder encoder,
            String username,
            String password,
            Role role) {

        if (repository.findByUsername(username).isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(encoder.encode(password));
            user.setRole(role);

            repository.save(user);
        }
    }
}









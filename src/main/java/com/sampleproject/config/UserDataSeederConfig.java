package com.sampleproject.config;

import com.sampleproject.common.enums.Role;
import com.sampleproject.user.entity.User;
import com.sampleproject.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
public class UserDataSeederConfig {

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            List<UserSeed> seeds = List.of(
                    new UserSeed("admin@sample.com", "password123", "Super", "Admin", "1234567890", Role.ADMIN),
                    new UserSeed("owner1@sample.com", "password123", "John", "Owner 1", "1234567891", Role.OWNER),
                    new UserSeed("owner2@sample.com", "password123", "Kabir", "Owner 2", "1234567892", Role.OWNER)
            );

            for (UserSeed seed : seeds) {
                if (userRepository.findByEmail(seed.email()).isEmpty()) {

                    User user = User.builder()
                            .email(seed.email())
                            .password(passwordEncoder.encode(seed.password())) // Hash the password
                            .firstName(seed.firstName())
                            .lastName(seed.lastName())
                            .phone(seed.phone())
                            .role(seed.role())
                            .build();
                    userRepository.save(user);
                    log.info("Seeded user: {} {} ({})", seed.firstName(), seed.lastName(), seed.role());
                } else {
                    log.info("User with email {} already exists, skipping.", seed.email());
                }
            }
        };
    }

    // Simple record to hold seed data (removed 'id' since it's auto-generated)
    private record UserSeed(
            String email,
            String password,
            String firstName,
            String lastName,
            String phone,
            Role role
    ) {}
}

package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Role role = new Role(1L, "ROLE_USER");
        Role role2 = new Role(2L, "ROLE_ADMIN");
        User user = new User("admin", passwordEncoder.encode("admin"), "1995");
        User user2 = new User("user", passwordEncoder.encode("user"), "1998");
        user.setRoles(Set.of(role2));
        user2.setRoles(Set.of(role));
        roleRepository.save(role);
        roleRepository.save(role2);
        userRepository.save(user);
        userRepository.save(user2);
    }
}

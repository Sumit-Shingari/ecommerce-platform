package com.nagp.ecommerce_poc.service;

import com.nagp.ecommerce_poc.entity.Role;
import com.nagp.ecommerce_poc.entity.User;
import com.nagp.ecommerce_poc.repository.RoleRepository;
import com.nagp.ecommerce_poc.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void syncUser(String firebaseUid, String email) {

        Optional<User> existing = userRepository.findByFirebaseUid(firebaseUid);

        if (existing.isPresent()) {
            return;
        }

        User user = new User();
        user.setFirebaseUid(firebaseUid);
        user.setEmail(email);

        user = userRepository.save(user);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(userRole);

        userRepository.save(user);
    }
}

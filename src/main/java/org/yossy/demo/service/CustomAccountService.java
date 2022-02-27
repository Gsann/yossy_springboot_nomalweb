package org.yossy.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yossy.demo.db.entity.User;
import org.yossy.demo.db.repository.UserRepository;

@Service("CustomAccountService")
public class CustomAccountService implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
   }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void register(String name, String email, String password, String[] roles) {
        userRepository.save(new User(name, email, passwordEncoder.encode(password), roles));
    }
}

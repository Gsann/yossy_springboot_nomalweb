package org.yossy.demo.service;

import java.util.List;
import java.util.Optional;

import org.yossy.demo.db.entity.User;

public interface AccountService {
    public List<User> findAll();
    public Optional<User> findByEmail(String email);
    public void register(String name, String email, String password, String[] roles);
}

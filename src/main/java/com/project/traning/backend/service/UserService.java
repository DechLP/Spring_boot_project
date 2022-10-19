package com.project.traning.backend.service;

import com.project.traning.backend.entity.User;
import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.exception.UserException;
import com.project.traning.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User update(User user) {
        return repository.save(user);
    }

    public User updateName(String id, String name) throws BaseException {
        Optional<User> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }

        User user = opt.get();
        user.setName(name);

        return repository.save(user);
    }

    public void deletedById(String id) {
        repository.deleteById(id);
    }

    public boolean matchPassword(String rawPassword, String encodePassword) {
        return passwordEncoder.matches(rawPassword, encodePassword);
    }

    public User create(String email, String password, String name) throws BaseException {

        if (Objects.isNull(email)) {
            // throw error
            throw UserException.createEmailNull();
        }

        if (Objects.isNull(password)) {
            // throw error
            throw UserException.createPasswordNull();
        }

        if (Objects.isNull(name)) {
            // throw error
            throw UserException.createNameNull();
        }

        // verify

        if (repository.existsByEmail(email)) {
            throw UserException.createEmailDuplicated();
        }

        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);

        return repository.save(entity);
    }

}

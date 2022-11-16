package com.project.traning.backend.service;

import com.project.traning.backend.entity.User;
import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.exception.UserException;
import com.project.traning.backend.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
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

    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public Optional<User> findById(String id) {
        log.info("Load User From DB: " + id);
        return repository.findById(id);
    }

    public Optional<User> findByToken(String token) {
        return repository.findByToken(token);
    }

    public User update(User user) {
        return repository.save(user);
    }

    @CachePut(value = "user", key = "#id")
    public User updateName(String id, String name) throws BaseException {
        Optional<User> opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw UserException.notFound();
        }

        User user = opt.get();
        user.setName(name);

        return repository.save(user);
    }

    @CacheEvict(value = "user", key = "#id")
    public void deletedById(String id) {
        repository.deleteById(id);
    }

    @CacheEvict(value = "user", allEntries = true)
    public void deleteAll() {

    }

    public boolean matchPassword(String rawPassword, String encodePassword) {
        return passwordEncoder.matches(rawPassword, encodePassword);
    }

    public User create(String email, String password, String name, String token, Date tokenExpireDate) throws BaseException {

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
        entity.setToken(token);
        entity.setTokenExpire(tokenExpireDate);

        return repository.save(entity);
    }

}

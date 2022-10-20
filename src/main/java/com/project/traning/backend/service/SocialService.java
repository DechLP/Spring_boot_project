package com.project.traning.backend.service;

import com.project.traning.backend.entity.Social;
import com.project.traning.backend.entity.User;
import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.exception.UserException;
import com.project.traning.backend.repository.SocialRepository;
import com.project.traning.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SocialService {

    private final SocialRepository repository;

    public SocialService(SocialRepository repository) {
        this.repository = repository;
    }

    public Optional<Social> findByEmail(User user) {
        return repository.findByUser(user);
    }

    public Social create(User user, String facebook, String line, String instagram, String tiktok) {
        Social entity = new Social();

        entity.setUser(user);
        entity.setFacebook(facebook);
        entity.setLine(line);
        entity.setInstagram(instagram);
        entity.setTiktok(tiktok);

        return repository.save(entity);
    }

}

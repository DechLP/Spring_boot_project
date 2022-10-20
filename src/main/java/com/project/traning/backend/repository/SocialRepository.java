package com.project.traning.backend.repository;

import com.project.traning.backend.entity.Social;
import com.project.traning.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialRepository extends CrudRepository<Social, String> {

    Optional<Social> findByUser(User user);

}

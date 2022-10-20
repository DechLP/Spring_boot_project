package com.project.traning.backend.repository;

import com.project.traning.backend.entity.Address;
import com.project.traning.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, String> {

    List<Address> findByUser(User user);

}

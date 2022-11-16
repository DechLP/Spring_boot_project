package com.project.traning.backend.mapper;

import com.project.traning.backend.entity.User;
import com.project.traning.backend.model.MRegisterResponse;
import com.project.traning.backend.model.MUserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    MRegisterResponse toRegisterResponse(User user);

    MUserProfile toUserProfile(User user);

}

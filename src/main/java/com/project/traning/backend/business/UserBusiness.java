package com.project.traning.backend.business;

import com.project.traning.backend.entity.User;
import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.exception.FileException;
import com.project.traning.backend.exception.UserException;
import com.project.traning.backend.mapper.UserMapper;
import com.project.traning.backend.model.MLoginRequest;
import com.project.traning.backend.model.MRegisterRequest;
import com.project.traning.backend.model.MRegisterResponse;
import com.project.traning.backend.service.TokenService;
import com.project.traning.backend.service.UserService;
import com.project.traning.backend.util.SecurityUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserBusiness {

    private final UserService userService;

    private final TokenService tokenService;

    private final UserMapper userMapper;

    public UserBusiness(UserService userService, TokenService tokenService, UserMapper userMapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
    }

    public String refreshToken() throws BaseException {
        Optional<String> opt = SecurityUtil.getCurrentUserId();
        if (opt.isEmpty()) {
            throw UserException.unAuthorized();
        }
        String userId = opt.get();

        Optional<User> optUser = userService.findById(userId);
        if (optUser.isEmpty()) {
            throw UserException.notFound();
        }

        User user = optUser.get();
        return tokenService.tokenize(user);
    }

    public MRegisterResponse register(MRegisterRequest request) throws BaseException {
        User user = userService.create(request.getEmail(), request.getPassword(), request.getName());

        return userMapper.toRegisterResponse(user);
    }

    public String login(MLoginRequest request) throws BaseException {
        // validate request

        // verify database
        Optional<User> opt = userService.findByEmail(request.getEmail());
        if (opt.isEmpty()) {
            // throw error
            throw UserException.loginFailEmailNotFound();
        }

        User user = opt.get();
        if ( !userService.matchPassword(request.getPassword(), user.getPassword())) {
            // throw error
            throw UserException.loginFailPasswordIncorrect();
        }

        return tokenService.tokenize(user);
    }

    public String uploadProfilePicture(MultipartFile file) throws BaseException {
        if (file == null) {
            // throw error
            throw FileException.fileNull();
        }

        if (file.getSize() > 1048576 * 2) {
            // throw error
            throw FileException.fileMaxSize();
        }

        String contentType = file.getContentType();
        if (contentType == null) {
            // throw error
            throw FileException.unsupported();
        }

        List<String> supportedTypes = Arrays.asList("image/jpeg", "image/png");
        if (!supportedTypes.contains(contentType)) {
            // throw error
            throw FileException.unsupported();
        }

        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";

    }

}

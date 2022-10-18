package com.project.traning.backend.business;

import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.exception.UserException;
import com.project.traning.backend.model.MRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TestBusiness {

    public String register(MRegisterRequest request) throws BaseException {
        if (request == null) {
            throw UserException.requestNull();
        }

        if (Objects.isNull(request.getEmail())) {
            throw UserException.emailNull();
        }
        return "";
    }

}

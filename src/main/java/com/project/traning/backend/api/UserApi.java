package com.project.traning.backend.api;

import com.project.traning.backend.business.UserBusiness;
import com.project.traning.backend.entity.User;
import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.model.MLoginRequest;
import com.project.traning.backend.model.MRegisterRequest;
import com.project.traning.backend.model.MRegisterResponse;
import com.project.traning.backend.model.TestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserApi {

//    METHOD 1 >> field Injection
//    @Autowired
//    private TestBusiness business;

    //    METHOD 2 >> Constructor Injection
    private final UserBusiness business;

    public UserApi(UserBusiness business) {
        this.business = business;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MLoginRequest request) throws BaseException {
        String response = business.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<MRegisterResponse> register(@RequestBody MRegisterRequest request) throws BaseException {
        MRegisterResponse response = business.register(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException {
        String response = business.refreshToken();
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<String> uploadProfilePicture(@RequestPart MultipartFile file) throws BaseException {
        String response = business.uploadProfilePicture(file);
        return ResponseEntity.ok(response);
    }

}

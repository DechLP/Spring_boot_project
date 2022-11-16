package com.project.traning.backend.api;

import com.project.traning.backend.business.UserBusiness;
import com.project.traning.backend.entity.User;
import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/profile")
    public ResponseEntity<MUserProfile> getMyUserProfile() throws BaseException {
        MUserProfile response = business.getMyUserProfile();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<MUserProfile> updateMyUserProfile(@RequestBody MUpdateUserProfileRequest request) throws BaseException {
        MUserProfile response = business.updateMyUserProfile(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<MLoginResponse> login(@RequestBody MLoginRequest request) throws BaseException {
        MLoginResponse response = business.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<MRegisterResponse> register(@RequestBody MRegisterRequest request) throws BaseException {
        MRegisterResponse response = business.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    public ResponseEntity<MActivateResponse> activate(@RequestBody MActivateRequest request) throws BaseException {
        MActivateResponse response = business.activate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<Void> resendActivationEmail(@RequestBody MResendActivationEmailRequest request) throws BaseException {
        business.resendActivationEmail(request);
        return ResponseEntity.status(HttpStatus.OK).build();
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

    @DeleteMapping("/test-delete")
    public ResponseEntity<Void> testDeleteMyAccount() throws BaseException {
        business.testDeleteMyAccount();
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

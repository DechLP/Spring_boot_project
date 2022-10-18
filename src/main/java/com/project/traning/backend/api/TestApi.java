package com.project.traning.backend.api;

import com.project.traning.backend.business.TestBusiness;
import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.model.MRegisterRequest;
import com.project.traning.backend.model.TestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestApi {

//    METHOD 1 >> field Injection
//    @Autowired
//    private TestBusiness business;

    //    METHOD 2 >> Constructor Injection
    private final TestBusiness business;

    public TestApi(TestBusiness business) {
        this.business = business;
    }

    @GetMapping
    public TestResponse test() {
        TestResponse response = new TestResponse();
        response.setName("Dech");
        response.setFood("Chicken");
        return response;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody MRegisterRequest request) throws BaseException {
        String response;
        response = business.register(request);
        return ResponseEntity.ok(response);
    }

}

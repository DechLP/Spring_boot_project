package com.project.traning.backend;

import com.project.traning.backend.business.EmailBusiness;
import com.project.traning.backend.exception.BaseException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestEmailBusiness {

    @Autowired
    private EmailBusiness emailBusiness;


    @Order(1)
    @Test
    void testSendActivateEmail() throws BaseException {
        emailBusiness.sendActivateUserEmail(
                TestData.email,
                TestData.name,
                TestData.token
        );
    }

    interface TestData {

        String email = "dechbodin34@gmail.com";

        String name = "Dechbodin Lumpai";

        String token = "ADFKJEP@$HIGIHSIHER324987";

    }
}

package com.project.traning.backend;

import com.project.traning.backend.entity.Address;
import com.project.traning.backend.entity.Social;
import com.project.traning.backend.entity.User;
import com.project.traning.backend.exception.BaseException;
import com.project.traning.backend.service.AddressService;
import com.project.traning.backend.service.SocialService;
import com.project.traning.backend.service.UserService;
import com.project.traning.backend.util.SecurityUtil;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TestUserService {

    @Autowired
    private UserService userService;

    @Autowired
    private SocialService socialService;

    @Autowired
    private AddressService addressService;

    @Order(1)
    @Test
    void testCreate() throws BaseException {
        String token = SecurityUtil.generateToken();
        User user = userService.create(
                TestCreateData.email,
                TestCreateData.password,
                TestCreateData.name,
                token,
                new Date()
        );

        // check not null
        Assertions.assertNotNull(user);
        Assertions.assertNotNull(user.getId());

        // check equals
        Assertions.assertEquals(TestCreateData.name, user.getName());
        Assertions.assertEquals(TestCreateData.email, user.getEmail());

        boolean isMatched = userService.matchPassword(TestCreateData.password, user.getPassword());
        Assertions.assertTrue(isMatched);

    }

    @Order(2)
    @Test
    void testUpdate() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        User updateUser = userService.updateName(user.getId(), TestUpdateData.name);

        Assertions.assertNotNull(updateUser);
        Assertions.assertEquals(TestUpdateData.name, updateUser.getName());

    }

    @Order(3)
    @Test
    void testCreateSocial() throws BaseException {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        Social social = user.getSocial();
        Assertions.assertNull(social);

        social = socialService.create(
                user,
                SocialTestCreateData.facebook,
                SocialTestCreateData.line,
                SocialTestCreateData.instagram,
                SocialTestCreateData.tiktok
        );

        Assertions.assertNotNull(social);
        Assertions.assertEquals(SocialTestCreateData.facebook, social.getFacebook());


    }

    @Order(4)
    @Test
    void testCreateAddress() {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();

        List<Address> addresses = user.getAddresses();
        Assertions.assertTrue(addresses.isEmpty());

        createAddress(user, AddressTestCreateData.line1, AddressTestCreateData.line2, AddressTestCreateData.zipcode);
        createAddress(user, AddressTestCreateData2.line1, AddressTestCreateData2.line2, AddressTestCreateData2.zipcode);


    }

    private void createAddress(User user, String line1, String line2, String zipcode) {
        Address address = addressService.create(
                user,
                line1,
                line2,
                zipcode
        );
        Assertions.assertNotNull(address);
        Assertions.assertEquals(address.getLine1(), line1);
        Assertions.assertEquals(address.getLine2(), line2);
        Assertions.assertEquals(address.getZipcode(), zipcode);
    }


    @Order(9)
    @Test
    void testDelete() {
        Optional<User> opt = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(opt.isPresent());

        User user = opt.get();
        // check social
        Social social = user.getSocial();
        Assertions.assertNotNull(social);
        Assertions.assertEquals(social.getFacebook(), SocialTestCreateData.facebook);
        // check address
        List<Address> addresses = user.getAddresses();
        Assertions.assertNotNull(addresses);
        Assertions.assertFalse(addresses.isEmpty());
        Assertions.assertEquals(2, addresses.size());



        userService.deletedById(user.getId());

        Optional<User> optDelete = userService.findByEmail(TestCreateData.email);
        Assertions.assertTrue(optDelete.isEmpty());


    }

    interface TestCreateData {

        String email = "dech@test.com";

        String password = "12345#test";

        String name = "Dechbodin Lumpai";

    }

    interface SocialTestCreateData {

        String facebook = "dechbodinFaceBook";

        String line = "";

        String instagram = "";

        String tiktok = "";

    }

    interface AddressTestCreateData {

        String line1 = "80/32";

        String line2 = "Prathumtanee";

        String zipcode = "12150";

    }

    interface AddressTestCreateData2 {

        String line1 = "80/321";

        String line2 = "Prathumtanee1";

        String zipcode = "121501";

    }

    interface TestUpdateData {

        String name = "Dechbodin Lumpai Developer";

    }

}

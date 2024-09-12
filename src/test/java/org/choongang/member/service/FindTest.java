package org.choongang.member.service;

import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.services.MemberSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
//@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FindTest {

    @Autowired
    private MemberSaveService saveService;

    private RequestJoin form;

    @BeforeEach
    void init() {
        for (long i = 1L; i <= 10L; i++) {
            form = new RequestJoin();
            form.setEmail("user" + i + "@test.org");
            form.setPassword("_aA12345678");
            form.setConfirmPassword(form.getPassword());
            form.setUserName("사용자" + i);
            form.setMobile("010-1000-1000");
            form.setJob("PROFESSOR");
            form.setBirth(LocalDate.of(1990, 1, 1));
            form.setGender("MALE");
            form.setAgree(true);
            System.out.println(form);
            saveService.save(form);
        }
    }

    @Test
    @DisplayName("이메일 찾기 테스트")
    void testGetEmail() throws Exception {
        String userName = "사용자1";
        LocalDate birth = LocalDate.of(1990, 1, 1);
        String expectedEmail = "user1@test.org";
    }
}

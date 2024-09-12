package org.choongang.member.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.choongang.member.constants.Job;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.member.services.MemberInfoService;
import org.choongang.member.services.MemberSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
//@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MemberSaveService saveService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberInfoService memberInfoService;

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
    @DisplayName("회원 가입 테스트")
    void joinTest() throws Exception {
        String params = om.writeValueAsString(form);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.forName("UTF-8"))
                        .content(params))
                .andDo(print());
    }

    @Test
    @DisplayName("직업으로 회원 목록 조회 테스트")
    public void testGetUsersByJob() {
        Job job = Job.PROFESSOR;
        List<Member> member = memberInfoService.getUsersByJob(job);
        assertEquals(10, member.size());
        System.out.println(member);
    }

    @Test
    @DisplayName("회원 이메일로 직업 조회 테스트")
    void testGetJobByEmailFound() {
            String email = "user1@test.org";
            Job job = memberInfoService.getJobByEmail(email);
            assertEquals(Job.PROFESSOR, job);
        System.out.println(job);
    }
}

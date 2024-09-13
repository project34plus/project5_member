package org.choongang.member.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.choongang.member.constants.Gender;
import org.choongang.member.constants.Job;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.member.services.MemberSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    private RequestJoin form;
    private RequestUpdate updateForm;

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
            List<String> _interests = new ArrayList<>();
            _interests.add("interest1");
            _interests.add("interest2");
            form.setInterests(_interests);
            System.out.println(form);
            saveService.save(form);
        }

        updateForm = new RequestUpdate();
        updateForm.setUserName("새로운 사용자");
        updateForm.setMobile("010-9999-9999");
        updateForm.setJob("LIBRARIAN");
        updateForm.setGender("FEMALE");
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
    @DisplayName("회원 정보 수정 테스트")
    @WithMockUser(username = "user01@test.org")
    void updateMemberTest() throws Exception {
        String email = "user1@test.org";

        String updateParams = om.writeValueAsString(updateForm);
        mockMvc.perform(MockMvcRequestBuilders.put("/account/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.forName("UTF-8"))
                        .content(updateParams))
                .andDo(print());

        Member updatedMember = memberRepository.findByEmail(email).orElseThrow();
        assert "새로운 사용자".equals(updatedMember.getUserName());
        assert "010-9999-9999".equals(updatedMember.getMobile());
        assert Job.LIBRARIAN.equals(updatedMember.getJob());
        assert Gender.FEMALE.equals(updatedMember.getGender());
        System.out.println(updatedMember);
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void withdrawTest() throws Exception {
        String email = "user1@test.org";

        mockMvc.perform(MockMvcRequestBuilders.patch("/account/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(Charset.forName("UTF-8")))
                .andDo(print())
                .andExpect(status().isOk());

        Member member = memberRepository.findByEmail(email).orElseThrow();
        assert member.getDeletedAt() != null;
    }
}

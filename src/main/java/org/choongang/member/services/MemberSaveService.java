package org.choongang.member.services;

import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.ApiRequest;
import org.choongang.member.MemberUtil;
import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Gender;
import org.choongang.member.constants.Job;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.controllers.RequestUpdate;
import org.choongang.member.entities.Member;
import org.choongang.member.exceptions.InterestSaveFailException;
import org.choongang.member.exceptions.MemberNotFoundException;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.thisis.entities.Interests;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;
    private final ApiRequest apiRequest;

    /**
     * 회원 가입 처리
     *
     * @param form
     */
    public void save(RequestJoin form) {
        Member member = new ModelMapper().map(form, Member.class);
        member.setGender((Gender.valueOf(form.getGender())));
        String hash = passwordEncoder.encode(form.getPassword()); // BCrypt 해시화
        member.setPassword(hash);
        if (form.getInterests() != null) {
            interestsSave(member, form.getInterests());
        }
        System.out.println("form : " + form);
        System.out.println(member);
        save(member);
    }

    public void save(Member member) {

        // 휴대전화번호 숫자만 기록
        String mobile = member.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        String gid = member.getGid();
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();
        member.setGid(gid);

        Authority authority = Objects.requireNonNullElse(member.getAuthorities(), Authority.USER);
        member.setAuthorities(authority);

        if (member.getInterests() != null) {
            ApiRequest result = apiRequest.request("/interest/update/" + member.getEmail(), "thesis-service", HttpMethod.PATCH, member.getInterests());
            if (result != null && !result.getStatus().is2xxSuccessful()) {
                System.out.println(result);
                throw new InterestSaveFailException();
            }


        }

        memberRepository.saveAndFlush(member);
    }

    /**
     * 회원 정보 수정
     *
     * @param form
     */
    public void save(RequestUpdate form) {

        String email = null;
        if (memberUtil.isAdmin() && StringUtils.hasText(form.getEmail())) {
            email = form.getEmail();
        } else {
            Member member = memberUtil.getMember();
            email = member.getEmail();
        }

        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        String password = form.getPassword();
        String mobile = form.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
        }
        member.setUserName(form.getUserName());
        member.setMobile(mobile);
        member.setJob(form.getJob() == null ? null : Job.valueOf(form.getJob()));
        member.setGender(form.getGender() == null ? null : Gender.valueOf(form.getGender()));

        if (StringUtils.hasText(password)) {
            String hash = passwordEncoder.encode(password);
            member.setPassword(hash);
        }

        //interests
        List<Interests> interests = new ArrayList<Interests>();
        form.getInterests().stream().filter(StringUtils::hasText).forEach(i -> interests.add(new Interests(i, member.getEmail())));
        System.out.println("interests : " + interests);
        member.setInterests(interests);
        Authority authority = StringUtils.hasText(form.getAuthority()) ? Authority.valueOf(form.getAuthority()) : Authority.USER;
        member.setAuthorities(authority);

        save(member);

    }


    private void interestsSave(Member member, List<String> interests) {
        List<Interests> targetInterests = new ArrayList<>();
        for (String interest : interests) {
            Interests _interest = new Interests(interest, member.getEmail());
            targetInterests.add(_interest);
        }
        member.setInterests(targetInterests);
    }

    public void changeStatus(String email, Authority authority) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        member.setAuthorities(authority);

        memberRepository.saveAndFlush(member);
    }
}

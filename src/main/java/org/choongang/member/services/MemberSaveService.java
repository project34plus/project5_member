package org.choongang.member.services;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Belonging;
import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.controllers.RequestUpdate;
import org.choongang.member.entities.Belongings;
import org.choongang.member.entities.Member;
import org.choongang.member.exceptions.MemberNotFoundException;
import org.choongang.member.repositories.BelongingRepository;
import org.choongang.member.repositories.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberSaveService {
    private final MemberRepository memberRepository;
    //private final AuthoritiesRepository authoritiesRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberUtil memberUtil;
    private final BelongingRepository belongingRepository;
    /**
     * 회원 가입 처리
     *
     * @param form
     */
    public void save(RequestJoin form) {
        Member member = new ModelMapper().map(form, Member.class);
        String hash = passwordEncoder.encode(form.getPassword()); // BCrypt 해시화
        member.setPassword(hash);

        save(member, List.of(Authority.USER));

        List<Belonging> belongings = form.getBelongings();
        if (belongings != null && !belongings.isEmpty()) {
            saveBelongings(member, belongings);
        }
    }

    /**
     * 회원 정보 수정
     * @param form
     */
    public void save(RequestUpdate form, List<Authority> authorities) {
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

        if (StringUtils.hasText(password)) {
            String hash = passwordEncoder.encode(password);
            member.setPassword(hash);
        }

        save(member, authorities);

        List<Belonging> belongings = form.getBelongings();
        if (belongings != null && !belongings.isEmpty()) {
            saveBelongings(member, belongings);
        }
    }

    public void saveBelongings(Member member, List<Belonging> belongings) {

        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        if (!memberRepository.existsById(member.getSeq())) {
            throw new IllegalArgumentException("Member does not exist");
        }

        belongings.forEach(i->{
            BelongingId id = new BelongingId(member,i);
            belongingRepository.deleteById(id);
        });

        List<Belongings> newBelongings = belongings.stream()
                .map(belonging -> new Belongings(member, belonging))
                .collect(Collectors.toList());

        belongingRepository.saveAllAndFlush(newBelongings);
    }


    public void save(RequestUpdate form) {
        save(form, null);
    }

    public void save(Member member, List<Authority> authorities) {

        // 휴대전화번호 숫자만 기록
        String mobile = member.getMobile();
        if (StringUtils.hasText(mobile)) {
            mobile = mobile.replaceAll("\\D", "");
            member.setMobile(mobile);
        }

        String gid = member.getGid();
        gid = StringUtils.hasText(gid) ? gid : UUID.randomUUID().toString();
        member.setGid(gid);

        memberRepository.saveAndFlush(member);


        // 권한 추가, 수정 S
//        if (authorities != null) {
//            List<Authorities> items = authoritiesRepository.findByMember(member);
//            authoritiesRepository.deleteAll(items);
//            authoritiesRepository.flush();
//
//            items = authorities.stream().map(a -> Authorities.builder()
//                    .member(member)
//                    .authority(a)
//                    .build()).toList();
//
//            authoritiesRepository.saveAllAndFlush(items);
//        }
        // 권한 추가, 수정 E
    }
}

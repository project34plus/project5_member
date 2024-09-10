package org.choongang.member.services;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.constants.Authority;
import org.choongang.member.entities.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDeleteService {
    private final MemberUtil memberUtil;

    public void delete(Member member, List<Authority> authorities) {

//        /* 권한 수정 */
//        if (authorities != null) {
//            List<Authority> items = authoritiesRepository.findByMember(member);
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
    }

    /* 회원 탈퇴 처리 */
    public void withdraw() {
        Member member = memberUtil.getMember();

        if (memberUtil.isLogin()) {
            member.setDeletedAt(LocalDateTime.now());
        }

        delete(member, null);
    }
}

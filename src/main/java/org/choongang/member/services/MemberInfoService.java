package org.choongang.member.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.global.ListData;
import org.choongang.global.Pagination;
import org.choongang.member.MemberInfo;
import org.choongang.member.constants.Authority;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JPAQueryFactory queryFactory;
    private final HttpServletRequest request;

    /* 회원 정보가 필요할 때마다 호출 되는 메서드 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Authority authority = Objects.requireNonNullElse(member.getAuthorities(), Authority.USER);

        List<SimpleGrantedAuthority> authorities =  List.of(new SimpleGrantedAuthority(authority.name()));

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }


    /**
     * 회원 목록 조회
     *
     * @param search
     * @return
     */
    @Transactional
    public ListData<Member> getList(MemberSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;
        int offset = (page - 1) * limit;

        /* 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;

        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt.toUpperCase() : "ALL";
        if (StringUtils.hasText(skey)) {
            skey = skey.trim();
            StringExpression expression = null;
            if (sopt.equals("ALL")) { // 통합 검색
                expression = member.email.concat(member.userName)
                        .concat(member.mobile);
            } else if (sopt.equals("name")) {
                expression = member.userName;
            }
            andBuilder.and(expression.contains(skey));
        }

        /* 검색 처리 E */

        List<Member> items = queryFactory.selectFrom(member)
//                .leftJoin(member.authority)
                .fetchJoin()
                .where(andBuilder)
                .offset(offset)
                .limit(limit)
                .orderBy(member.createdAt.desc())
                .fetch();

        long total = memberRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, (int)total, 10, limit, request);

        return new ListData<>(items, pagination);
    }
}
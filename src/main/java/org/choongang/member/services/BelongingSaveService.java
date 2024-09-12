package org.choongang.member.services;

import lombok.RequiredArgsConstructor;
import org.choongang.member.entities.Member;
import org.choongang.member.exceptions.MemberNotFoundException;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.thisis.entities.Field;
import org.choongang.thisis.services.ThesisInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BelongingSaveService {

    private final ThesisInfoService thesisInfoService;
    private final MemberRepository memberRepository;

    public List<Field> getListFields() {
        return thesisInfoService.getFields();
    }

    public void assignFieldToMember(String email, String _field) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        Field field = thesisInfoService.getFields().stream()
                .filter(f -> f.getId().equals(_field))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        member.setFields(_field);
        memberRepository.save(member);
    }
}

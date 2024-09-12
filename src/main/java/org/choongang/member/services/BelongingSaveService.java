package org.choongang.member.services;

import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BelongingSaveService {
    private final Utils utils;

    // 소속 분야 저장
    /*
    public void saveBelonging(List<String> _belongings, String email) {
        if (!StringUtils.hasText(email)) {
            throw new BadRequestException(utils.getMessage("NotBlank.Email"));
        }
        List<Belongings> list = belongingRepository.findAllByEmail(email);
        if (!list.isEmpty()) { // 기존 소속 분야가 존재하는 경우
            belongingRepository.deleteByEmail(email);//삭제 후
            belongingRepository.flush();
        }
        saveBelongings(_belongings, email); // 소속 분야 저장

    }

    private void saveBelongings(List<String> belonging, String email) {
        belonging.forEach(i -> {
        });
    }*/
}

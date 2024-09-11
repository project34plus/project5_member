package org.choongang.thisis.services;

import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.ApiRequest;
import org.choongang.thisis.entities.Interests;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestSaveService {

    private final ApiRequest apiRequest;

    /**
     * 관심 분야 저장 / 수정
     */
    public Interests save(List<Interests> _interests, String email) {
        ApiRequest result = apiRequest.request("/interest/" + email, "thesis-service");

        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toObj(Interests.class);
        }

        return null;
    }
}

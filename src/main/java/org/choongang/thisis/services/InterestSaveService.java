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
     * 회원 관심사 조회
     */
    public Interests interestInfo (String email) {
        ApiRequest result = apiRequest.request("/interest/" + email, "thesis-service");

        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toObj(Interests.class);
        }

        return null;
    }


    /**
     * 회원 관심사 수정
     */

    private Interests update(List<String> _interests, String email) {
        ApiRequest result = apiRequest.request("/interest/update/" + email, "thesis-service");

        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toObj(Interests.class);
        }

        return null;
    }
}

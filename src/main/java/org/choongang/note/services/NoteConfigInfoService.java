package org.choongang.note.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.execution.Util;
import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.global.rests.JSONData;
import org.choongang.note.entities.Note;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoteConfigInfoService {
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final Utils utils;


    /**
     * 노트 설정 조회
     *
     * @param bid
     * @return
     */

    public Optional<Note> get(String bid) {
        try {
            String url = utils.adminUrl("/api/note/config/" + bid);
            ResponseEntity<JSONData> response = restTemplate.getForEntity(url, JSONData.class);
            if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
                JSONData jsonData = response.getBody();
                if (!jsonData.isSuccess()) {
                    return Optional.empty();
                }
                Object data = jsonData.getData();

                Note note = om.readValue(om.writeValueAsString(data), Note.class);

                return Optional.ofNullable(note);
    }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}

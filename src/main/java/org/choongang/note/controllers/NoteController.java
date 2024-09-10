package org.choongang.note.controllers;


import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.JSONData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    @GetMapping("/list/{nid}")
    public JSONData list() {

    return null;
    }
}
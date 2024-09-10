package org.choongang.note.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.repositories.NoteDataRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteSaveService {

    private final HttpServletRequest request;
    private final PasswordEncoder encoder;
    private final NoteConfigInfoService configInfoService;
    private final NoteDataRepository boardDataRepository;



}
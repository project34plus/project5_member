package org.choongang.note.services;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.choongang.repositories.NoteDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteInfoService {
    private final JPAQueryFactory queryFactory;
    private final NoteDataRepository repository;
    private final NoteConfigInfoService configInfoService;
}

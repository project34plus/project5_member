package org.choongang.member.repositories;

import org.choongang.member.entities.BelongingId;
import org.choongang.member.entities.Belongings;
import org.choongang.member.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BelongingRepository extends JpaRepository<Belongings, BelongingId> {
    List<Belongings> findByMember(Member member);
    void deleteAllByMember(Member member);
}

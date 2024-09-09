package org.choongang.member.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.choongang.member.constants.Belonging;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BelongingId {
    private Member member;
    private Belonging belonging;
}

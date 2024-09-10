package org.choongang.member.entities;

import lombok.*;
import org.choongang.member.constants.Belonging;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BelongingId  implements Serializable {
    private Member member;
    private Belonging belonging;
}

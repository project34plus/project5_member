package org.choongang.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.member.constants.Belonging;

import java.io.Serializable;

@Data
@Entity
@Builder
@IdClass(BelongingId.class)
@NoArgsConstructor
public class Belongings implements Serializable {
    @Id
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_seq")
    private Member member;

    @Id
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Belonging belonging;

    public Belonging getBelongings() {
        return belonging;
    }

    public Belongings(Member member, Belonging belonging) {
        this.member = member;
        this.belonging = belonging;
    }
}

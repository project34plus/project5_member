package org.choongang.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.member.constants.Belonging;

@Data
@Entity
@Builder
@IdClass(BelongingId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Belongings {
    @Id
    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    private Member member;

    @Id
    @Column(length=20)
    @Enumerated(EnumType.STRING)
    private Belonging belonging;
}

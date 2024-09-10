package org.choongang.note.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.member.constants.Authority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(indexes = @Index(name="idx_note_basic", columnList = "listOrder DESC, createdAt DESC"))
public class Note {
    @Id
    @Column(length = 30)
    private String nid; // 노트 아이디

    @Column(length=65, nullable = false)
    private String gid = UUID.randomUUID().toString();

    private int listOrder; // 진열 가중치 - 노트 항목이 정렬되는 순서 결정 (높을 수록 앞에)

    @Column(length=60, nullable = false)
    private String nName; // 노트 이름

    private boolean active; // 사용 여부

    private int rowsPerPage = 20; // 1 페이지 노트 수

    private int pageCountPc = 10; // PC 페이지 구간 갯수( 페이지네이션 표시 할 페이지 번호갯수)

    private int pageCountMobile = 5; // Mobile 페이지 구간 갯수

//    private boolean useReply; //  *답글 사용 여부 제외 *

//    private boolean useComment; // *댓글 사용 여부 제외 *

    private boolean useEditor; // 에디터 사용 여부

    private boolean useUploadImage; // 이미지 청부 사용 여부

    private boolean useUploadFile; // 파일 첨부 사용 여부

    @Column(length=10, nullable = false)
    private String locationAfterWriting = "list"; //  작성 후 이동 위치 (노트 목록 or 노트 보기)

//    private boolean showListBelowView; // *노트 보기 하단 목록 노출 여부 제외*

    private String skin = "default"; //스킨 기본값= 노트

    @Lob
    private String category; // 노트 분류

    @Enumerated(EnumType.STRING) //enum 클래스 상수를 문자열로 변환해서 db에 저장
    @Column(length=20, nullable = false)
    private Authority listAccessType = Authority.USER; // 권한 설정 - 글목록

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority viewAccessType = Authority.USER; // 권한 설정 - 글보기

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Authority writeAccessType = Authority.USER; // 권한 설정 - 글쓰기

//    @Enumerated(EnumType.STRING)
//    @Column(length=20, nullable = false)
//    private Authority replyAccessType = Authority.USER; // 권한 설정 - 답글
//
//    @Enumerated(EnumType.STRING)
//    @Column(length=20, nullable = false)
//    private Authority commentAccessType = Authority.USER; // 권한 설정 - 댓글


//    @Lob
//    private String htmlTop; // 게시판 상단 HTML
//
//    @Lob
//    private String htmlBottom; // 게시판 하단 HTML

    /**
     * 분류 List 형태로 변환
     *
     * @return
     */
    @JsonIgnore
    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();

        if (StringUtils.hasText(category)) {
            categories = Arrays.stream(category.trim().split("\\n"))
                    .map(s -> s.trim().replaceAll("\\r", ""))
                    .toList();
        }

        return categories;
    }


}

package org.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board") //board 객체는 문자열 처리 예외
public class Reply extends BaseEntity{//댓글 입력, 수정일 사용을 위해 BaseEntity 상속받음

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동번호 생성
    private Long rno;
    private String text;
    private String replyer;
    
    //board 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
}

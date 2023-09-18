package org.zerock.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //문자열 변환 하지 않음(writer > 객체화(member))
public class Board extends BaseEntity { //BaseEntity를 상속받아 자동 날짜 만들어짐

    @Id //PK > 댓글 연결
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동번호 생성
    private Long bno;
    private String title;
    private String content;

    //작성자 이메일 FK 처리
    /*@ManyToOne
    private Member writer; //멤버 Entity에 PK를 FK와 연결함.*/

    @ManyToOne(fetch = FetchType.LAZY) //지연 로딩을 사용(호출할때먼 조인)
    private Member writer; //멤버 Entity에 PK를 FK와 연결함.

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}

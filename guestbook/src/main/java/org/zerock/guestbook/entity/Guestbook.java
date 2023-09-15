package org.zerock.guestbook.entity;

import lombok.*;

import javax.persistence.*;

@Entity // jpa 엔티티
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Guestbook extends BaseEntity { //직접 데이터베이스 테이블에 관여

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동번호 생성(마리아db용)
    private Long gno;

    @Column(length = 100, nullable = false)
    private String title ;

    @Column(length = 1500, nullable = false)
    private String content ;

    @Column(length = 50, nullable = false)
    private String writer ;

    //세터용 메서드 추가 > 제목과 내용이 수정되면 수정일자가 변경되야 한다.(Auditing)
    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }
}

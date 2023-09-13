package org.zerock.ex2.entity;

import lombok.*;

import javax.persistence.*;

@Entity //db에 관여하는 클래스
@Table(name = "tbl_memo") //테이블 명을 지정
@ToString //lombok에서 String 문자열로 변경
@Getter //lombok에서 Getter 생성
@Builder // 빌더 패턴(@AllArgsConstructor, @NoArgsConstructor 필수)
@AllArgsConstructor // 생성자 자동 생성
@NoArgsConstructor // 기본 생성자 X, @AllArgsConstructor 같이 사용
public class Memo {

    @Id //pk로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno; //메모 일련번호

    @Column(length = 200, nullable = false) // 필드에 크기와 null 허용 여부, false = not null
    private String memoText;


}

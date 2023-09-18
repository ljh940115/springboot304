package org.zerock.board.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter //DB에 내용을 가져옴
@ToString //객체에 대한 문자열 변환
public class Member extends BaseEntity{//가입일, 회원정보 수정일 사용을 위해 BaseEntity 상속받음

    //최근 트랜드는 이메일 주소로 기본키를 만듬

    @Id //PK 지정
    private String email;
    private String password;
    private String name;
}

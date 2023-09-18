package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired //자동 생성자 생성
    private MemberRepository memerRepository;

    @Test
    public void insertMembers(){
        IntStream.rangeClosed(1, 1).forEach(i -> {//1부터 100까지 반복한다. 람다식 사용
            Member member = Member.builder()//멤버 객체를 생성해 빌더를 이용해 값을 넣는다.
                    .email("user" + i + "@example.com")
                    .password("1111")
                    .name("user"+i)
                    .build();

            memerRepository.save(member);//member 값을 memerRepository에서 사용한다. update : 없으면 생성하고 있으면 수정한다.\
        });
    }
}

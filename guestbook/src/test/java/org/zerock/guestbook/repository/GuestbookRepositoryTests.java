package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest //스프링 부트용 테스트
public class GuestbookRepositoryTests {

    @Autowired //생성자 자동주입
    private GuestbookRepository guestbookRepository;

    //더미데이터 300개 추가
    @Test
    public void insertDumies() {

        IntStream.rangeClosed(1, 300).forEach(i -> {

            Guestbook guestbook = Guestbook.builder()
                    .title("Title....." + i)
                    .content("Content......" + i)
                    .writer("user" + (i / 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        //300번의 방명록을 찾아 null 여부 판단

        if (result.isPresent()) { //Option에 객체가 있으면
            Guestbook guestbook = result.get(); //찾은 객체를 넣는다.
            guestbook.changeTitle("수정된 제목!!! 300");
            guestbook.changeContent("수정된 내용!!! 300");
            guestbookRepository.save(guestbook); //있으면 수정 없으면 생성
            //update set > insert into
        }
    }

    @Test
    public void testQuery1() { //단항 검색용

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());//페이징 처리 기법 명시
        QGuestbook qGuestbook = QGuestbook.guestbook;
        //동적 처리하기 위해서 Q도메인 클래스를 얻어온다. (엔티티에 있는 객체와 필드 사용)

        String keyword = "1"; //키워드를 1로 지정
        BooleanBuilder builder = new BooleanBuilder(); //while문처럼 조건들을 넣어주는 컨테이너
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        //원하는 조건은 필드 값과 같이 결합해서 생성 > 제목에 1 값을 찾는다.
        builder.and(expression); //객체 2개를 연결하여 where문을 처리
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        //Repository에 내장된 메서드를 활용하여 값을 찾아 페이지 처리용 객체에 담는다.

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);

        });
    }

    //다항 검색용(제목, 내용에 키워드가 있고 gno가 0보다 크다.)
    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(1, 10, Sort.by("gno").descending());//페이징 처리 기법 명시
        QGuestbook qGuestbook = QGuestbook.guestbook;
        //동적 처리하기 위해서 Q도메인 클래스를 얻어온다. (엔티티에 있는 객체와 필드 사용)

        String keyword = "11"; //키워드를 1로 지정
        BooleanBuilder builder = new BooleanBuilder(); //while문처럼 조건들을 넣어주는 컨테이너
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exWriter = qGuestbook.writer.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent).or(exWriter);
        //원하는 조건은 필드 값과 같이 결합해서 생성 > 제목과 내용에 11 값을 찾는다.
        builder.and(exAll); //객체 2개를 연결하여 where문을 처리
        builder.and(qGuestbook.gno.gt(0L));//gno가 0보다 큰 값을 처리(pk 인덱싱 기법)

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        //Repository에 내장된 메서드를 활용하여 값을 찾아 페이지 처리용 객체에 담는다.

        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }
}

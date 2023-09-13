package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest //테스트 코드임을 명시
public class MemoRepositoryTests {

    @Autowired //생성자 자동 주입
    MemoRepository memoRepository ; //객체 생성

    @Test //메서드 별 테스트 junit
    public void testClass() {
    System.out.println(memoRepository.getClass().getName());
    //메모 리포지토리 인터페이스 객체에 있는 클래스의 이름을 출력
    }

    @Test
    // 메모 테이블에 더미 데이터 입력용
    public void testInsertDummies(){
        IntStream.rangeClosed(1, 100).forEach(i -> { // 1부터 100까지.반복(람다식으로)
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            //메모 객체를 반복 생성, 빌더 패턴으로 메모 텍스트 넣는다.
            memoRepository.save(memo);
            //메모 리포지토리에 save 메서드를 이용하여 insert 진행
        });
    }// 메모 100개 객체 생성 후 save 작업 완료

    @Test
    public void testSelect(){ //번호를 이용하여 메모를 찾아본다.
        //데이터 베이스에는 1번부터 100번까지 데이터 존재
        Long mno = 100L; //찾을 번호를 변수에 넣음

        Optional<Memo> result = memoRepository.findById(mno);
        //memoRepository.findById(number) : 번호에 맞는 객체를 가져온다. (select from where mno = 100L)

        System.out.println("====================================================================");
        System.out.println("=================================출력================================");
        if (result.isPresent()){
            Memo memo = result.get();//찾은 객체를 memo라는 변수에 넣는다.
            System.out.println(memo);//memo 객체를 출력해본다. @ToString
        }
    }

    @Transactional
    @Test
    public void testselect2(){
        Long mno = 99L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("====================================================================");
        System.out.println("=================================출력================================");
        System.out.println(memo);
        //실행시켜보면 출력 구분선 아래에 Hibernate이 돌아간다. Transactional이므로 System.out.println(memo);이 필요할 때부터
        //Memo memo = memoRepository.getOne(mno);가 돌아가기 때문
    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(101L).memoText("Update Text...101").build();//빌더 페던으로 객체 생성

        System.out.println(memoRepository.save(memo));//메모 리포지토리를 호출해서 save
    }

    @Test
    public void testDelete(){
        Long mno = 101L;

        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(3, 10); //1페이지, 10개 리스트

        Page<Memo> result = memoRepository.findAll(pageable); //위에서 만든 Pageable 객체에 10개를 result에 넣는다.

        System.out.println(result);
        System.out.println("=====================================================");
        System.out.println("총 페이지 수 : " + result.getTotalPages());
        System.out.println("전체 개수" + result.getTotalElements());
        System.out.println("현재 페이지 번호" + result.getNumber());
        System.out.println("페이지 당 데이터 개수 " + result.getSize());
        System.out.println("이전 페이지 유무 " + result.hasPrevious());
        System.out.println("다음 페이지 유무 " + result.hasNext());
        System.out.println("시작 페이지 유무 " + result.isFirst());
        System.out.println("마지막 페이지 유무 " + result.isLast());
        System.out.println("페이지 유무 " + result.getContent());

        System.out.println("=====================리스트 확인용====================");
        for (Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();//번호를 기준으로 내림차순 정렬 명시
        Sort sort2 = Sort.by("memoText").ascending();//텍스트 오름차순
        Sort sortAll = sort1.and(sort2);//2가지 정렬 기법 활용
        //명시한다음 페이징 기법에 붙인다.

        Pageable pageable = PageRequest.of(0, 10, sortAll);
        //페이지, 사이즈, 정렬 기법 명시

        Page<Memo> result = memoRepository.findAll(pageable);
        //페이징 + 정렬 기법을 활용하여 findAll 메서드 실행 후 result에 넣는다.

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethod(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        for (Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(1, 10, Sort.by("Mno").descending());
        //페이지 레이아웃 구현 및 정렬

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        //게시물 중에 10 ~ 50 까지의 객체를 페이징 처리하여 page 객체로 생성한다.

        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Commit //트랜젝션 성공 시 영구 저장
    @Transactional //두개 이상의 쿼리문 true면 동시 처리
    @Test
    public void testDeleteQueryMethod(){
        //10보다 작은 Mno 값을 제거한다. 있을 경우 지워야하기 때문에 트랜젝션 사용해야 함

        memoRepository.deleteMemoByMnoLessThan(10L);
        //게시물 중에 10보다 작은 객체를 삭제한다.
    }
}
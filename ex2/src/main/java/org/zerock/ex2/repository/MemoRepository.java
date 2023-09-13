package org.zerock.ex2.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
    // extends JpaRepository<Memo, Long> : <엔티티 객체, pk 타입>

    //쿼리 메서드 구현(메서드 명이 쿼리 문으로 됨)
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    //from(70) 과 to(80) 값을 받아 범위를 측정하고 Mno를 기준으로 내림차순 정렬

    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
    //Long from(10), Long to(50), Pageable pageable(페이징 기법과 정렬)

    void deleteMemoByMnoLessThan(Long num);
    //매개값으로 넘어오는 숫자보다 작은 레코드를 삭제

    @Query("select m from Memo m order by m.mno desc")//테이블명 대신에 엔티티 클래스 활용
    List<Memo> getListDesc();

    @Transactional //트렌젝션 처리
    @Modifying //수정 처리
    @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
    int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);
    //값을 받아온다.

    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = : #{#param.mno}")
    int updateMemoext(@Param("param") Memo memo);
    //객체를 받아온다.

    @Query(value = "select m from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);
    //페이징 처리할때는 카운트 쿼리 무조건 넣어준다.

    @Query(value = "select m.mno, m.memoText, current_date from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);
    //엔티티를 직접 지명하지 않고 Object를 사용하면 유동적으로 사용할 수 있다.
}


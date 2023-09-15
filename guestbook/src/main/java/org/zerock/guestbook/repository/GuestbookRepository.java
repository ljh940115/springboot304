package org.zerock.guestbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long>
        , QuerydslPredicateExecutor<Guestbook> {
    //JpaRepository<Guestbook, Long> -> 엔티티 객체 테이블 구현
    //QuerydslPredicateExecutor<Guestbook> -> Guestbook을 쿼리dsl 사용
    //curd를 담당하는 jpa


}

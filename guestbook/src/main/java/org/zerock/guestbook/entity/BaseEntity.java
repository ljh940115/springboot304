package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass //직접 테이블을 관여하지 않음
@EntityListeners(value = {AuditingEntityListener.class}) //감시용 -> main클래스에 @EnableJpaAuditing필수
@Getter //게터 활용
abstract class BaseEntity {  // 등록시간과 수정시간을 담당하는 추상클래스

    @CreatedDate //생성시만 반응
    @Column(name = "regdate", updatable = false) // 업데이트 불가
    private LocalDateTime regDate ; //등록시간

    @LastModifiedDate //수정시에 마지막 시간용
    @Column(name="moddate")
    private LocalDateTime modDate ; //수정시간

}

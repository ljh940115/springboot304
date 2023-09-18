package org.zerock.guestbook.dto;

//앤티티 클래스와 유사한 필드, 값 변경 가능

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor //@Builder랑 세트
@NoArgsConstructor //@Builder랑 세트
@Data //Lombok을 사용하여 Getter, Setter, ToString 등을 사용함
public class GuestbookDTO {

    private Long gno;
    private String title, content, writer;
    private LocalDateTime regDate, modDate;

}

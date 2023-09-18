package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

public interface GuestbookService {
    //방명록의 CRUD를 담당, 서비스는 사람용(create, modify) *DB(insert, update)

    Long register(GuestbookDTO dto);//추상 메서드, Long인 이유 : 등록하면 n번에 등록되었습니다. 라고 나와야하기 때문에, 받는다.

// PageRequestDTO를 파라미터로 PageResultDTO 리턴 타입으로 사용하는 getList 설계
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

//    게시물 조회 처리
    GuestbookDTO read(Long gno);

//    게시물 수정 처리
    void modify(GuestbookDTO dto);

//    게시물 삭제 처리
    void remove(Long gno);

    //디폴트 메서드 선언(구현 클래스에 강제 주입 없이 혼자 동작할 수 있도록 하는 용도), 전역 설정이라고 보면 될 듯, 리턴한다.
    default Guestbook dtoToEntity(GuestbookDTO dto){
        //게스트 북이라는 앤티티를 앤티티로 만든다.
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
                return entity;
                //해석 : 게스트북이라는 앤티티에
    }

// 엔티티 객체를 DTO 객체로 변환하는 클래스
    default GuestbookDTO entityToDto(Guestbook entity) {
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}

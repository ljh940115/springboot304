package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

@SpringBootTest //스프링 부트 테스트용
public class GuestbookServiceTests {

    @Autowired //생성자 자동 주입
    private GuestbookService service;

    @Test
    public void testRegister(){
        //dto로 들어온 객체가 entitiy로 전달되는 코드 테스트
        GuestbookDTO dto = GuestbookDTO.builder()
                .title("dtoToEntity 활용 제목...")
                .content("dtoToEntity 활용 내용...")
                .writer("user0")
                .build(); //dto 객체 생성
        System.out.println(service.register(dto));
        //서비스 클래스의 register 메서드를 실행(입력 > dto, 출력 entity)
    }

/*    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }
    }*/

// 목록 데이터 페이지 처리 테스트
@Test
public void testList(){

    PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

    PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

    System.out.println("PREV: "+resultDTO.isPrev());//1페이지는 이전으로 가는 링크 X
    System.out.println("NEXT: "+resultDTO.isNext());
    System.out.println("TOTAL: " + resultDTO.getTotalPage());//전체 페이지 개수

    System.out.println("-------------------------------------");
    for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
        System.out.println(guestbookDTO);
    }

    System.out.println("화면페 출력될 페이지 번호========================================");
    resultDTO.getPageList().forEach(i -> System.out.println(i));
}

    @Test
    public void testSearch(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")   //검색 조건 t, c, w, tc, tcw ..
                .keyword("11")  // 검색 키워드
                .build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);

        System.out.println("PREV: "+resultDTO.isPrev());
        System.out.println("NEXT: "+resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("========================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }

}

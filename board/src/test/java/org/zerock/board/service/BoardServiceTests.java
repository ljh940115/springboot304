package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .title("제목 등록 테스트")
                .content("내용 등록 테스트")
                .writerEmail("유저1@example.com")
                .build();

        Long bno = boardService.register(dto);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet() {
        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    //삭제 테스트
    @Test
    public void testRemove(){
        Long bno = 101L;
        boardService.removeWithReplies(bno);
    }

    //게시글 수정 테스트
    @Test
    public void testModify() {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(1L)
                .title("제목 수정 테스트")
                .content("내용 수정 테스트")
                .build();

        boardService.modify(boardDTO);
    }
}

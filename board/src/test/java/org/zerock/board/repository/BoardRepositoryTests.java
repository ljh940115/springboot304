package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired //자동 생성자 생성
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1, 1).forEach(i -> {//1부터 100까지 생성
            Member member = Member.builder().email("user" + i + "@example.com").build();//fk에 pk값을 넣기 위해 member 객체 생성

            //bno는 자동생성됨
            Board board = Board.builder()
                    .title("제목" + i)
                    .content("내용" + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Transactional //동시 처리(보드 처리하면서 멤버 처리)
    @Test
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(100L); //DB에 존재하는 번호 사용

        Board board = result.get(); // Optional<Board> get은 객체를 가져옴

        System.out.println(board); //board 객체 toString
        System.out.println(board.getWriter()); //board에 연결된 member.email 객체 호출 > Member(email=유저100example.com, password=1111, name=유저100) 이메일만 호출했는데 다 가져옴, 취약점
    }

    @Test
    public void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .title("제목 등록 테스트")
                .content("내용 등록 테스트")
                .writerEmail("유저1@example.comm")
                .build();

    }

    @Test
    public void testSearch1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPage() {

        Pageable pageable =
                PageRequest.of(0,10,
                        Sort.by("bno").descending()
                                .and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);

    }

}

package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;
import org.zerock.board.entity.Reply;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ReplyRepositoryTests {

    @Autowired //자동 생성자 생성
    private ReplyRepository replyRepository;

    @Test
    public void insertReply(){
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long bno = (long)(Math.random()*100)+1;

            Board board = Board.builder().bno(bno).build();

            Reply reply = Reply.builder()
                    .text("댓글..." + i)
                    .board(board)
                    .replyer("guest")
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    public void readReply1(){

        Optional<Reply> result = replyRepository.findById(1L);

        Reply reply = result.get();

        System.out.println(reply);
        System.out.println(reply.getBoard());
        //결론 : 댓글을 호춣했는데 board와 member까지 호출됨
        //사용자 입장에서 . 찍으면 다 나오기 때문에 편리하지만
        //컴퓨터 입장에서 과부하
    }


}

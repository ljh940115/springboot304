package org.zerock.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;
import org.zerock.guestbook.repository.GuestbookRepository;

import java.util.Optional;
import java.util.function.Function;

@Service        //서비스 계층 동작 명시
@Log4j2         //로그 사용 명시
@RequiredArgsConstructor //의존성 자동 주입(구현 객체용)
public class GuestbookServiceImpl implements GuestbookService {

    //jpa 객체 처리(private final 처리 필수, 안하면 보안 문제 생길 수 있음)
    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {    //강제 구현된 메서드(구현 메서드)
        log.info("==================등록된 dto 객체==================");
        log.info(dto); //ToString 이용하여 객체 출력
        Guestbook entity = dtoToEntity(dto); //dto로 받은 값을 entity로 저장

        log.info(entity);

        //jpa를 통한 데이터 삽입
        repository.save(entity);
        log.info("등록된 방명록의 번호 : " + entity.getGno());
        return entity.getGno(); // 차후 등록된 bno를 처리하는 용도 dto.getGno()
    }

// GetList()는 entityDTO()를 이용해서 PageResultDTO로 구성, PageResultDTO는 쿼리 결과 Page<Entity>와 Function을 전달해서
// 엔티티 객체들을 DTO 리스트로 변환, 페이지 처리
    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색 조건 처리

        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable);//Querydsl 사용
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {

        Optional<Guestbook> result = repository.findById(gno);

        return result.isPresent()? entityToDto(result.get()): null;
    }

    @Override
    public void remove(Long gno) {

        repository.deleteById(gno);

    }

    @Override
    public void modify(GuestbookDTO dto) {

        //업데이트 하는 항목은 '제목', '내용'

        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()){

            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);

        }
    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){

        String type = requestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestbook.gno.gt(0L); // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){ //검색 조건이 없는 경우
            return booleanBuilder;
        }


        //검색 조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}

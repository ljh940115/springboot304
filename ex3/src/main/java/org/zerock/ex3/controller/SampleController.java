package org.zerock.ex3.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex3.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller //컨트롤러 담당(분기)
@RequestMapping("/sample") //http://localhost:80/sample/???? 처리
@Log4j2 //로그 출력용
public class SampleController {

    @GetMapping("/ex1") // //http://localhost:80/sample/ex1.html 처리
    public void ex1() {

        log.info("ex1..............메서드 실행 및 ex1.html 리턴");
        //html코드는 templates 아래에 sample 폴더에 작성 *jsp는 views 아래에 작성했었다.
    }

    @GetMapping({"/ex2"})
    public void exModel(Model model){
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().
                mapToObj(i -> {
                    SampleDTO dto = SampleDTO.builder()
                    .sno(i)
                    .first("First.." + i)
                    .last("Last.." + i)
                    .regTime(LocalDateTime.now())
                    .build();
                    return dto;
        }).collect(Collectors.toList());
        model.addAttribute("list", list);
    }

    @GetMapping({"/exInline"})
    public String exInline(RedirectAttributes redirectAttributes){
        log.info("exInline..........................");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First...100")
                .last("Last...100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result", "success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3(){

        log.info("ex3");
    }
}
//SampleDTO 타입 객체 20개 추가, Model에 담아 전송

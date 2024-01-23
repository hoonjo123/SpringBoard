package com.spring.board.author.Controller;

import com.spring.board.author.dto.AuthorListResDto;
import com.spring.board.author.dto.AuthorSaveReqDto;
import com.spring.board.author.dto.AuthorUpdateReqDto;
import com.spring.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }



    @PostMapping("/author/create")
    public String authorSave(AuthorSaveReqDto authorSaveReqDto){
        authorService.save(authorSaveReqDto);
        return "redirect:/author/list";
    }

    @GetMapping("/author/list")
    public String authorList(Model model){
        List<AuthorListResDto> authorListResDtos = authorService.findAll();
        model.addAttribute("authorList", authorListResDtos);
        return "author/author-list";
    }

    @GetMapping("/author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model){
        System.out.println(id);
        model.addAttribute("author", authorService.findById(id));
        return "author/author-detail";
    }

    @GetMapping("/author/create")
    public String authorCreate(){
        return "author/author-create";
    }

    @GetMapping("/author/edit/{id}") // 회원 정보 수정 페이지로 이동
    public String authorEdit(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "author/author-edit";
    }

    @PostMapping("/author/update") // 회원 정보 업데이트
    public String authorUpdate(AuthorUpdateReqDto authorUpdateReqDto) {
        authorService.update(authorUpdateReqDto);
        return "redirect:/author/detail/" + authorUpdateReqDto.getId();
    }


    @PostMapping("/author/delete/{id}") // 회원 삭제
    public String authorDeleteConfirm(@PathVariable Long id) {
        authorService.delete(id);
        return "redirect:/author/list";
    }

}

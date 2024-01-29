package com.spring.board.author.Controller;

import com.spring.board.author.dto.AuthorSaveReqDto;
import com.spring.board.author.dto.AuthorUpdateReqDto;
import com.spring.board.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Slf4j
@Controller
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("author/create")
    public String authorCreate(){
        return "author/author-create";
    }

    @PostMapping("author/create")
    public String authorSave(AuthorSaveReqDto authorSaveReqDto, RedirectAttributes redirectAttributes){
        try {
            authorService.save(authorSaveReqDto);
            return "redirect:/author/list";
        } catch (IllegalArgumentException | IllegalAccessException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            log.error(e.getMessage());
            return "redirect:/author/create"; // 오류 시 양식 페이지로 리디렉션
        }
    }



    @GetMapping("author/list")
    public String authorList(Model model) {
        model.addAttribute("authorList", authorService.findAll());
        return "author/author-list";
    }

    @GetMapping("author/detail/{id}")
    public String authorDetail(@PathVariable Long id, Model model){
        System.out.println(id);
        model.addAttribute("author", authorService.findAuthorDetail(id));
        return "author/author-detail";
    }

    @PostMapping("author/{id}/update")
    public String authorUpdate(@PathVariable Long id, AuthorUpdateReqDto authorUpdateReqDto, Model model) {
        authorService.update(id, authorUpdateReqDto);
        model.addAttribute("author", authorUpdateReqDto);
        return "redirect:/author/detail/" + id;
    }

    @GetMapping("author/delete/{id}")
    public String authorDelete(@PathVariable Long id){
        authorService.delete(id);
        return "redirect:/author/list";
    }
}
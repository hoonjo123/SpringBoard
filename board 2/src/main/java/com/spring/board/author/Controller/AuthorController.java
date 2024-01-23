package com.spring.board.author.Controller;

import com.spring.board.author.dto.AuthorListResDto;
import com.spring.board.author.dto.AuthorSaveReqDto;
import com.spring.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }



    @PostMapping("/author/save")
    @ResponseBody
    public String authorSave(AuthorSaveReqDto authorSaveReqDto){
        authorService.save(authorSaveReqDto);
        return "ok";
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

    @GetMapping("/author/save")
    public String authorCreate(){
        return "author/author-save";
    }
}

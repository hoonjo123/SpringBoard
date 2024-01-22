package com.spring.board.author.Controller;

import com.spring.board.author.dto.AuthorListResDto;
import com.spring.board.author.dto.AuthorSaveReqDto;
import com.spring.board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
    @ResponseBody
    public List<AuthorListResDto> authorList(){
        return authorService.findAll();
    }
}

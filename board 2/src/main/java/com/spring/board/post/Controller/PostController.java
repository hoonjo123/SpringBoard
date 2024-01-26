package com.spring.board.post.Controller;

import com.spring.board.post.dto.PostCreateReqDto;
import com.spring.board.post.dto.PostUpdateReqDto;
import com.spring.board.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {
    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("post/create")
    public String postCreate() {
        return "post/post-create";
    }

    @PostMapping("/post/create")
    public String createPost(PostCreateReqDto postCreateReqDto, RedirectAttributes redirectAttributes) {
        try {
            postService.save(postCreateReqDto);
            return "redirect:/post/list";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/post/create"; // 예외 발생 시 포스트 생성 페이지로 리디렉션
        }
    }


    @GetMapping("post/list")
    public String postList(Model model, @PageableDefault(size = 5, sort = "createdTime",direction= Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("postList",postService.findByAppointment(pageable));
        return "post/post-list";
//        return postService.findAll();
    }

//    @GetMapping("/json/post/list")
//    //localhost:8080/post/list?size=xx&page=xx&sort=xx, desc)
//    @ResponseBody
//    public Page<PostListResDto> postList(Pageable pageable) {
//        Page<PostListResDto> postListResDtos = postService.findAllJson(pageable);
//        return postListResDtos;
//    }

    @GetMapping("post/detail/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findPostDetail(id));
        return "post/post-detail";
//        return postService.findPostDetail(id);
    }

    @PostMapping("post/{id}/update")
    public String postUpdate(@PathVariable Long id, PostUpdateReqDto postUpdateReqDto, Model model) {
        postService.update(id, postUpdateReqDto);
        model.addAttribute("post", postUpdateReqDto);
        return "redirect:/post/detail/" + id;
    }

    @GetMapping("post/delete/{id}")
    public String postDelete(@PathVariable Long id){
        postService.delete(id);
        return "redirect:/post/list";
    }

}
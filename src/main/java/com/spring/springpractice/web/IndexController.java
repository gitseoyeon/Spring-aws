package com.spring.springpractice.web;

import com.spring.springpractice.config.auth.LoginUser;
import com.spring.springpractice.config.auth.dto.SessionUser;
import com.spring.springpractice.service.posts.PostsService;
import com.spring.springpractice.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    // mustache 덕분에 문자열을 반환할 때 앞의 경로와 뒤의 파일 확장자는 자동으로 지정됨
    // View Resolver가 처리
    @GetMapping("/")
    // Model : 서버 템블릿 엔진에서 사용할 수 있는 객체를 저장
    // @LoginUser SessionUser user : 기존에 httpSession.getAttribute("user")로 가져오던 세션 정보 값이 개선됨
    // 이제는 어노 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있게 됨
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postsService.findAllDesc());

        /* SessionUser user = (SessionUser) httpSession.getAttribute("user"); // CustomOAuthUserService 에서 로그인 성공시 세션에 SessionUser 를 지정하도록 구성*/

        if (user != null) { // 세션에 저장된 값이 있을 때만 model에 userName으로 등록
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";
    }
}

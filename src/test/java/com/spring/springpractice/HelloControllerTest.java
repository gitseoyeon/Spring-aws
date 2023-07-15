package com.spring.springpractice;

import com.spring.springpractice.web.HelloController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebAppConfiguration
@RunWith(SpringRunner.class) // 스프링부트 테스트와 JUnit 사이에 연결자 역할을 함
@WebMvcTest(controllers = HelloController.class) // Web(Spring MVC) 어노테이션에 집중할 수 있는 어노테이션. controller 관련 어노테이션 사용 가능
public class HelloControllerTest {

    @Autowired // 스프링이 관리하는 빈(bean) 을 주입받음
    private MockMvc mvc; // 웹 API 를 테스트할 때 사용. 이 클래스를 통해 HTTP GET, POST 등에 대한 API 테스트 가능해짐

    @Test
    public void helloisReturn() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello")) // MockMvc 를 통해 /hello 주소로 HTTP GET 요청을 함
                .andExpect(status().isOk()) // mvc.perform 의 결과 검증. 200, 404, 500 등의 상태 검증. 여기서는 OK 즉 200인지 아닌지 검증
                .andExpect(content().string(hello)); // mvc.perform 결과 검증. Controller 에서 "hello"를 리턴하기 때문에 값이 맞는지 검증
    }

    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                // param : api 태스트 할 때 사용될 요청 파라미터 설정. 값은 String 만 허용
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // jsonPath : JSON 응답 값을 필드별로 검증할 수 있는 메서드. $를 기준으로 필드명 명시
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}

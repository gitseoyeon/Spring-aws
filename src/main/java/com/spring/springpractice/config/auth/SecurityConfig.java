package com.spring.springpractice.config.auth;

import com.spring.springpractice.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화 시켜줌
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                // h2-console 화면을 사용하기 위해 해당 옵션들을 disable 함
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests() // URL 별 권한 관리를 설정하는 옵션의 시작점
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 권한 관리 대상을 지정하는 옵션. URL, HTTP 메소드별 관리가 가능함
                    .anyRequest().authenticated() // anyRequest : 설정된 값들 이외 나머지 URL 들을 나타냄. authenticated()을 추가해서 나머지 URL 들은 모두 인증된 사용자들(로그인한 사용자) 에게만 허용
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃 기능에 대한 여러 설정들의 진입점. 로그아웃 성공시 / 주소로 이동함
                .and()
                    .oauth2Login() // OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService); // 소셜 로그인 성공시 후속 조취를 진행할 UserService 인터페이스의 구현체를 등록
    }
}

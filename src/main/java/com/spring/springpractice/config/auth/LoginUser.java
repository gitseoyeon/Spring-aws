package com.spring.springpractice.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 메소드 인자로 세션값을 바로 받을 수 있도록 함
@Target(ElementType.PARAMETER) // 이 어노테이션이 생성될 수 있는 위치 지정
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // @interface : 파일을 어노테이션 클래스로 지정함
}

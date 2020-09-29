package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.springframework.context.annotation.ComponentScan.Filter;

/**
 * Created by frenchline707@gmail.com on 2020-09-29
 * Blog : http://frenchline707.tistory.com
 * Github : http://github.com/frenchLineCigar
 */

/**
 * 컴포넌트 스캔의 탐색 위치와 기본 스캔 대상
 * 1) 탐색할 패키지의 시작 위치 지정 방법
 * 2) 컴포넌트 스캔 기본 대상
 */
@Configuration
@ComponentScan(
        basePackages = "hello.core.member",
        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class) // @Configuration 붙은 클래스는 제외 -> 기존 설정 코드들과 충돌을 피하기 위해
)
public class AutoAppConfig {

}





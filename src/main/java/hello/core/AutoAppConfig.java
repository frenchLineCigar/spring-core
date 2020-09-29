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
 * 컴포넌트 스캔과 의존관계 자동 주입
 */
@Configuration
@ComponentScan(
        excludeFilters = @Filter(type = FilterType.ANNOTATION, classes = Configuration.class) // @Configuration 붙은 클래스는 제외 -> 기존 설정 코드들과 충돌을 피하기 위해
)
public class AutoAppConfig {

}

    /**
     * `@ComponentScan` : @Component 애노테이션이 붙은 클래스를 찾아 자동으로 스프링 빈으로 등록해준다
     * -> excludeFilters : 제외할 항목을 지정
     *
     * 1. 컴포넌트 스캔을 사용하려면 먼저 `@ComponentScan`을 설정 정보에 붙여주면 된다.
     * 2. 기존의 AppConfig와는 다르게 @Bean으로 등록된 클래스가 하나도 없다!
     *
     * 참고: 컴포넌트 스캔을 사용하면 `@Configuration`이 붙은 설정 정보도 자동으로 등록되기 때문에, AppConfig, TestConfig 등
     * 앞서 만들어두었던 설정 정보도 함께 등록되고, 실행되어 버린다. 그래서 `excludeFilters`를 이용해서 설정정보는 컴포넌트 스캔 대상에서 제외했다.
     * 보통 설정 정보를 컴포넌트 스캔 대상에서 제외하지는 않지만, 기존 예제 코드를 최대한 남기고 유지하려면 이런 방법을 사용할 수 있다
     *
     * 3. 컴포넌트 스캔은 이름 그대로 `@Component` 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 자동 등록한다.
     * 참고: `@Configuration`이 컴포넌트 스캔의 대상이 된 이유도 `@Configuration` 소스코드를 열어보면 `@Component` 애노테이션이 분어있기 때문이다.
     *
     */




